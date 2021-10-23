package gcc.main.logic;

import gcc.main.logic.utils.Generators;
import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ReplicationLogicTest {

    @Test
    void shouldReplicateFirstLevel() {

        var mat = Generators.zeroMatrix(3);
        mat[1][1] = 1;

        List<Pack> packs = MatrixLogics.getPacks(mat);

        List<Pair<Integer, Integer>> activeServers = packs.stream()
                                                          .map(Pack::getActiveServer)
                                                          .collect(Collectors.toList());

        List<Pair<Integer, Integer>> peers = packs.stream()
                                                  .map(Pack::getPeersToUpdate)
                                                  .flatMap(Collection::stream)
                                                  .collect(Collectors.toList());

        Replication replication = new Replication(activeServers, peers, new HashMap<>());


        Assertions.assertEquals(1, replication.getActivePoints()
                                              .size());
        Assertions.assertEquals(4, replication.getPendingPeers()
                                              .size());

        Replication replication1 = ReplicationLogic.replicatev2(replication, 3);

        Assertions.assertEquals(5, replication1.getActivePoints()
                                               .size());
        Assertions.assertEquals(4, replication1.getPendingPeers()
                                               .size());

        Replication replication2 = ReplicationLogic.replicatev2(replication1, 3);

        Assertions.assertEquals(9, replication2.getActivePoints()
                                               .size());
        Assertions.assertEquals(0, replication2.getPendingPeers()
                                               .size());


    }

}
