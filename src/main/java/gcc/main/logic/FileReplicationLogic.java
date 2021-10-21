package gcc.main.logic;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final public class FileReplicationLogic {

    public static int ticksTillReplicationComplete(int[][] matrix) {

        var count = 0;
        while(true) {

            List<Pair<Integer, Integer>> pairs = MatrixLogics.peersToUpdateFromPacks(
                    MatrixLogics.getPacks(matrix));
            if(pairs.size() == 0){
                System.out.println("no more replications");
                break;
            }

            matrix = MatrixLogics.updatedMatrix(matrix, pairs);
            count++;
        }

        return count;
    }



}
