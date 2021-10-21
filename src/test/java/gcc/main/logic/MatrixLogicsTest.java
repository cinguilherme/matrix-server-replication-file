package gcc.main.logic;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatrixLogicsTest {

    private int[][] zeroMatrix() {
        var matrix = new int[3][3];
        matrix[0][0] = 0;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = 0;
        matrix[1][2] = 0;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 0;
        return matrix;
    }

    @Test
    void shouldHaveOnePackWith2Peers() {
        var matrix = zeroMatrix();
        matrix[0][0] = 1;

        List<Pack> packs = MatrixLogics.getPacks(matrix);

        assertEquals(1, packs.size());
        assertEquals(2, packs.get(0).getPeersToUpdate().size());
    }

    @Test
    void shouldHaveOnePackWith2PeersToo() {
        var matrix = zeroMatrix();
        matrix[2][2] = 1;

        List<Pack> packs = MatrixLogics.getPacks(matrix);

        assertEquals(1, packs.size());
        assertEquals(2, packs.get(0).getPeersToUpdate().size());
    }

    @Test
    void shouldEndupWith3UniquePeers() {
        var matrix = zeroMatrix();
        matrix[0][2] = 1;
        matrix[2][2] = 1;

        List<Pack> packs = MatrixLogics.getPacks(matrix);

        assertEquals(2, packs.size());
        assertEquals(2, packs.get(0).getPeersToUpdate().size());
        assertEquals(2, packs.get(1).getPeersToUpdate().size());

        List<Pair<Integer, Integer>> pairs = MatrixLogics.peersToUpdateFromPacks(packs);

        assertEquals(3, pairs.size());
    }

    @Test
    void shouldUpdateMatrixState() {

        var matrix = zeroMatrix();
        matrix[0][2] = 1;
        matrix[2][2] = 1;

        List<Pack> packs = MatrixLogics.getPacks(matrix);
        List<Pair<Integer, Integer>> pairs = MatrixLogics.peersToUpdateFromPacks(packs);

        var updateMatrix = MatrixLogics.updatedMatrix(matrix, pairs);

        assertEquals(0, updateMatrix[1][1]);
        assertEquals(1, updateMatrix[0][2]);
        assertEquals(1, updateMatrix[0][1]);
        assertEquals(1, updateMatrix[1][2]);
    }

}