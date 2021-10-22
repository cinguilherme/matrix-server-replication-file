package gcc.main.logic;

import org.javatuples.Pair;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

final public class FileReplicationLogic {

    public static int ticksTillReplicationComplete(int[][] matrix) {


        //get packs
        List<Pack> packs = MatrixLogics.getPacks(matrix);

        List<Pair<Integer, Integer>> activeServers = packs.stream()
                                                          .map(Pack::getActiveServer)
                                                          .collect(Collectors.toList());

        List<Pair<Integer, Integer>> peers = packs.stream()
                                                  .map(Pack::getPeersToUpdate)
                                                  .flatMap(Collection::stream)
                                                  .collect(Collectors.toList());


        Replication replication = new Replication(activeServers, peers);
        if (replication.getPendingPeers()
                       .size() == 0) {
            return 0;
        }

        var count = 0;
        while (replication.getPendingPeers()
                          .size() > 0) {

            replication = ReplicationLogic.replicatev2(replication, matrix.length);
            count++;
            System.out.println("replication tick number " + count);
        }

        return count;
    }
}
