package gcc.main.logic;

import gcc.main.logic.utils.Generators;
import org.junit.jupiter.api.Test;

import static gcc.main.logic.utils.Generators.zeroMatrix;
import static org.junit.jupiter.api.Assertions.*;

class FileReplicationLogicTest {

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