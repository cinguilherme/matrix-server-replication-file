package gcc.main.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.javatuples.Pair;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Pack {

    private Pair<Integer,Integer> activeServer;
    private List<Pair<Integer, Integer>> peersToUpdate;

}
