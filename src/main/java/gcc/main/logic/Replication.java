package gcc.main.logic;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Replication {

    private List<Pair<Integer, Integer>> activePoints;
    private List<Pair<Integer, Integer>> pendingPeers;
    private Map<Pair<Integer, Integer>, Integer> allActive;

    public Replication(List activePoints, List pendingPeers) {
        this.activePoints = activePoints;
        this.pendingPeers = pendingPeers;
        this.allActive = new HashMap<>();
    }
}
