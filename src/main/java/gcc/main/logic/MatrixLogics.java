package gcc.main.logic;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MatrixLogics {

    public static List<Pack> getPacks(int[][] matrix) {
        List<Pack> packs = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j] == 1) {
                    var pack = new Pack(new Pair<>(i, j), getPeers(matrix, new Pair<>(i,j)));
                    packs.add(pack);
                }
            }
        }

        return packs;
    }

    public static List<Pair<Integer, Integer>> peersToUpdateFromPacks(List<Pack> packs) {
        Set<Pair<Integer, Integer>> set = new HashSet<>();
        packs.stream()
             .map(p -> p.getPeersToUpdate())
                .flatMap(l -> l.stream())
                .forEach(p -> set.add(p));
        return set.stream().toList();
    }

    private static List<Pair<Integer, Integer>> getPeers(int[][] matrix, Pair<Integer, Integer> active) {
        var line = active.getValue0();
        var col = active.getValue1();
        List<Pair<Integer, Integer>> peers = new ArrayList<Pair<Integer, Integer>>();
        if(isThisPeerNeeded(matrix, line-1, col)){
            peers.add(new Pair<>(line-1, col));
        }
        if(isThisPeerNeeded(matrix, line+1, col)){
            peers.add(new Pair<>(line+1, col));
        }
        if(isThisPeerNeeded(matrix, line, col-1)){
            peers.add(new Pair<>(line, col-1));
        }
        if(isThisPeerNeeded(matrix, line, col+1)){
            peers.add(new Pair<>(line, col+1));
        }
        return peers;
    }

    private static boolean isThisPeerNeeded(int[][] matrix, int line, int col) {
        try {
            var v = matrix[line][col];
            return v == 0;
        } catch (RuntimeException rte) {
            return false;
        }
    }

}
