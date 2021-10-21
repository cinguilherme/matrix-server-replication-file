package gcc.main.logic;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final public class FileReplicationLogic {

    public static int ticksTillReplicationComplete(int[][] matrix) {

        List<Pair<Integer, Integer>> pairs = MatrixLogics.peersToUpdateFromPacks(
                MatrixLogics.getPacks(matrix));


        return 0;
    }



}
