package gcc.main.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileReplicationLogicTest {

    private int[][] zeroMatrix(int size) {
        var matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = 0;
            }
        }

        return matrix;
    }

    @Test
    void shouldBe2() {

        var matrix = zeroMatrix(3);
        matrix[1][1] = 1;

        int actual = FileReplicationLogic.ticksTillReplicationComplete(matrix);

        assertEquals(2, actual);
    }

    @Test
    void shouldBe2000(){
        int[][] matrix = zeroMatrix(2000);

        matrix[1000][1000] = 1;

        int actual = FileReplicationLogic.ticksTillReplicationComplete(matrix);

        assertEquals(2000, actual);
    }

}