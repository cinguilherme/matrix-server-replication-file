package gcc.main.logic;

import org.javatuples.Pair;
import java.util.List;

final public class FileReplicationLogic {

    public static int ticksTillReplicationComplete(int[][] matrix) {

        var count = 0;
        List<Pair<Integer, Integer>> allPointsWithoutFile =
                MatrixLogics.getAllPointsWithoutFile(matrix);

        List<Pack> packs = MatrixLogics.getPacks(matrix);

        List<Pair<Integer, Integer>> allPointsWithFile = MatrixLogics.peersToUpdateFromPacks(packs);

        while(true) {

            if(allPointsWithFile.size() == 0){
                System.out.println("no replications possible");
                break;
            }


            matrix = MatrixLogics.updatedMatrix(matrix, allPointsWithFile);
            count++;
        }

        return count;
    }
}
