package gcc.main.logic;

import org.javatuples.Pair;
import java.util.List;

final public class FileReplicationLogic {

    public static int ticksTillReplicationComplete(int[][] matrix) {

        var count = 0;
        List<Pair<Integer, Integer>> allPointsWithoutFile =
                MatrixLogics.getAllPointsWithoutFile(matrix);

        List<Pair<Integer, Integer>> pairs = MatrixLogics.peersToUpdateFromPacks(
                MatrixLogics.getPacks(matrix));

        while(true) {

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
