package gcc.main.logic;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReplicationLogic {

    public static Replication replicatev2(Replication replication, int matrixLimits) {

        System.out.println("actives");
        System.out.println(replication.getActivePoints());

        System.out.println("pending");
        System.out.println(replication.getPendingPeers());

        var active = new HashSet(replication.getActivePoints());
        active.addAll(replication.getPendingPeers());

        var newActives = active.stream()
                               .toList();
        System.out.println("new actives");
        System.out.println(newActives);


        var newPeers = genPeers(newActives, matrixLimits);

        System.out.println("new pendings");
        System.out.println(newPeers);

        return new Replication(newActives, newPeers);
    }

    private static List<Pair<Integer, Integer>> genPeers(
            List<Pair<Integer, Integer>> newActives, int matrixLimits) {

        Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
        newActives.forEach(it -> map.put(it, 1));

        return newActives.stream()
                         .map(v -> genPeerPerPoint(v, matrixLimits))
                         .flatMap(Collection::stream)
                         .filter(it -> map.get(it) == null)
                         .collect(Collectors.toSet())
                         .stream()
                         .toList();
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
        System.out.println(replicated);
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
