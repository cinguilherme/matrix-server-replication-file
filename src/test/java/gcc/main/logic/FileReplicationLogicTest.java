package gcc.main.logic;

import org.junit.jupiter.api.Test;

import static gcc.main.logic.utils.Generators.zeroMatrix;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileReplicationLogicTest {

    @Test
    void shouldBe2() {

        var matrix = zeroMatrix(3);
        matrix[1][1] = 1;

        int actual = FileReplicationLogic.ticksTillReplicationComplete(matrix);

        assertEquals(2, actual);
    }

    @Test
    void shouldBe100() {

        var matrix = zeroMatrix(100);
        matrix[50][50] = 1;

        int actual = FileReplicationLogic.ticksTillReplicationComplete(matrix);

        assertEquals(100, actual);
    }

    @Test
    void shouldBe500() {

        var matrix = zeroMatrix(500);
        matrix[250][250] = 1;

        int actual = FileReplicationLogic.ticksTillReplicationComplete(matrix);

        assertEquals(500, actual);
    }

    @Test
    void shouldBe2000() {
        int[][] matrix = zeroMatrix(2000);

        matrix[1000][1000] = 1;

        int actual = FileReplicationLogic.ticksTillReplicationComplete(matrix);

        assertEquals(2000, actual);
    }

}