package gcc.main.logic;

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

}