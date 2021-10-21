package gcc.main.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.javatuples.Pair;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Pack {

    private Pair<Integer,Integer> activeServer;
    private List<Pair<Integer, Integer>> peersToUpdate;

}
