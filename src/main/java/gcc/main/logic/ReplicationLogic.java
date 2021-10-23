package gcc.main.logic;

import gcc.main.logic.utils.MetricProfiling;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReplicationLogic {

    public static Replication replicatev2(Replication replication, int matrixLimits) {

        var metricN = new MetricProfiling();
        metricN.startMetric("gen new actives");
        var active = new HashSet(replication.getActivePoints());
        active.addAll(replication.getPendingPeers());

        List<Pair<Integer, Integer>> newActives = active.stream()
                                                        .toList();
        metricN.endMetric();

        List<Pair<Integer, Integer>> newPeers = genPeers(newActives, matrixLimits);

        // there is no need to keep the active list evergrowing,
        // we can discard the active which all peers are also active
        var map = replication.getAllActive();
        newActives.forEach(it -> map.put(it, 1));

        List<Pair<Integer, Integer>> cleanedActives = discardFullReplicatedPoints(newActives, map);

        return new Replication(cleanedActives, newPeers, map);
    }

    private static List<Pair<Integer, Integer>> discardFullReplicatedPoints(
            List<Pair<Integer, Integer>> active, Map<Pair<Integer, Integer>, Integer> map) {

        return active.stream()
                     .filter(v -> !allPeersAlreadyActive(v, map))
                     .collect(Collectors.toList());

    }

    private static boolean allPeersAlreadyActive(
            Pair<Integer, Integer> v, Map<Pair<Integer, Integer>, Integer> map) {
        var right = new Pair<>(v.getValue0() + 1, v.getValue1());
        var left = new Pair<>(v.getValue0() - 1, v.getValue1());
        var top = new Pair<>(v.getValue0(), v.getValue1() - 1);
        var bot = new Pair<>(v.getValue0(), v.getValue1() + 1);

        return map.get(right) == 1 && map.get(left) == 1 && map.get(top) == 1 && map.get(bot) == 1;
    }

    private static List<Pair<Integer, Integer>> genPeers(
            List<Pair<Integer, Integer>> newActives, int matrixLimits) {
        var metricM = new MetricProfiling();
        metricM.startMetric("gem map");
        Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
        newActives.forEach(it -> map.put(it, 1));
        metricM.endMetric();


        var metricF = new MetricProfiling();
        metricF.startMetric("filter pendings");
        List<Pair<Integer, Integer>> pairs = newActives.stream()
                                                       .map(v -> genPeerPerPoint(v, matrixLimits))
                                                       .flatMap(Collection::stream)
                                                       .filter(it -> map.get(it) == null)
                                                       .collect(Collectors.toSet())
                                                       .stream()
                                                       .toList();

        metricF.endMetric();

        return pairs;
    }

    private static List<Pair<Integer, Integer>> genPeerPerPoint(
            Pair<Integer, Integer> newPoint, int matrixLimits) {
        List<Pair<Integer, Integer>> peers = new ArrayList<>();

        if (withinLimits(newPoint.getValue0() - 1, matrixLimits)) {
            peers.add(new Pair<>(newPoint.getValue0() - 1, newPoint.getValue1()));
        }

        if (withinLimits(newPoint.getValue0() + 1, matrixLimits)) {
            peers.add(new Pair<>(newPoint.getValue0() + 1, newPoint.getValue1()));
        }

        if (withinLimits(newPoint.getValue1() - 1, matrixLimits)) {
            peers.add(new Pair<>(newPoint.getValue0(), newPoint.getValue1() - 1));
        }

        if (withinLimits(newPoint.getValue1() + 1, matrixLimits)) {
            peers.add(new Pair<>(newPoint.getValue0(), newPoint.getValue1() + 1));
        }

        return peers;
    }


    public static Replication replicate(List<Pack> previous, int matrixLimits) {
        List<Pack> replicated = new ArrayList<>();
        for (int i = 0; i < previous.size(); i++) {
            Pack pack = previous.get(i);
            pack.getPeersToUpdate()
                .stream()
                .forEach(p -> {
                    Pair<Integer, Integer> newPoint = new Pair<>(p.getValue0(), p.getValue1());
                    var peers = produceReplicatePeers(newPoint, matrixLimits);
                    Pack newPack = new Pack(new Pair<>(p.getValue0(), p.getValue1()), peers);
                    replicated.add(newPack);
                });
        }

        return new Replication();
    }

    private static List<Pair<Integer, Integer>> produceReplicatePeers(
            Pair<Integer, Integer> newPoint, int matrixLimits) {
        List<Pair<Integer, Integer>> peers = new ArrayList<>();

        if (withinLimits(newPoint.getValue0() - 1, matrixLimits)) {
            peers.add(new Pair<>(newPoint.getValue0() - 1, newPoint.getValue1()));
        }

        if (withinLimits(newPoint.getValue0() + 1, matrixLimits)) {
            peers.add(new Pair<>(newPoint.getValue0() + 1, newPoint.getValue1()));
        }

        if (withinLimits(newPoint.getValue1() - 1, matrixLimits)) {
            peers.add(new Pair<>(newPoint.getValue0(), newPoint.getValue1() - 1));
        }

        if (withinLimits(newPoint.getValue1() + 1, matrixLimits)) {
            peers.add(new Pair<>(newPoint.getValue0(), newPoint.getValue1() + 1));
        }

        return peers;
    }

    private static boolean withinLimits(int v, int matrixLimits) {
        return v >= 0 && v < matrixLimits;
    }

}
