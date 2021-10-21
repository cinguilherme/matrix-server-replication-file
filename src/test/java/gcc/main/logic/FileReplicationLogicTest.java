package gcc.main.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileReplicationLogicTest {

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
    void shouldBe2() {

        var matrix = zeroMatrix();
        matrix[1][1] = 1;

        int actual = FileReplicationLogic.ticksTillReplicationComplete(matrix);

        assertEquals(2, actual);
    }

}