package gcc.main.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.javatuples.Pair;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Replication {

    private List<Pair<Integer, Integer>> activePoints;
    private List<Pair<Integer, Integer>> pendingPeers;

}
