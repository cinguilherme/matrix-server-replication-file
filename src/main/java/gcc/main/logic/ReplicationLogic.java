package gcc.main.logic;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class ReplicationLogic {

    public static Replication replicate(List<Pack> previous, int matrixLimits) {
        List<Pack> replicated = new ArrayList<>();
        for (int i = 0; i < previous.size(); i++) {
            Pack pack = previous.get(i);
            pack.getPeersToUpdate().stream().forEach(p -> {
                Pair<Integer, Integer> newPoint = new Pair<>(p.getValue0(), p.getValue1());
                var peers = produceReplicatePeers(newPoint, matrixLimits);
                Pack newPack = new Pack(new Pair<>(p.getValue0(), p.getValue1()), peers);
                replicated.add(newPack);
            });
        }
        System.out.println(replicated);
        return new Replication();
    }

    private static List<Pair<Integer, Integer>> produceReplicatePeers(Pair<Integer, Integer> newPoint, int matrixLimits) {
        List<Pair<Integer, Integer>> peers = new ArrayList<>();

        if(withinLimits(newPoint.getValue0()-1, matrixLimits)) {
            peers.add(new Pair<>(newPoint.getValue0()-1, newPoint.getValue1()));
        }

        if(withinLimits(newPoint.getValue0()+1, matrixLimits)) {
            peers.add(new Pair<>(newPoint.getValue0()+1, newPoint.getValue1()));
        }

        if(withinLimits(newPoint.getValue1()-1, matrixLimits)) {
            peers.add(new Pair<>(newPoint.getValue0(), newPoint.getValue1()-1));
        }

        if(withinLimits(newPoint.getValue1()+1, matrixLimits)) {
            peers.add(new Pair<>(newPoint.getValue0(), newPoint.getValue1()+1));
        }

        return peers;
    }

    private static boolean withinLimits(int v, int matrixLimits) {
        return v >= 0 && v < matrixLimits;
    }

}
