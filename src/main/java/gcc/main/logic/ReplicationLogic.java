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

        var newActives = active.stream()
                .toList();
        metricN.endMetric();

        var newPeers = genPeers(newActives, matrixLimits);

        return new Replication(newActives, newPeers);
    }

    private static List<Pair<Integer, Integer>> genPeers(List<Pair<Integer, Integer>> newActives,
                                                         int matrixLimits) {
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

    private static List<Pair<Integer, Integer>> genPeerPerPoint(Pair<Integer, Integer> newPoint,
                                                                int matrixLimits) {
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
