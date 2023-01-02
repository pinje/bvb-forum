package nl.fontys.s3.bvbforum.domain.response.player;

import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.bvbforum.domain.Player;

import java.util.List;

@Data
@Builder
public class GetPlayersResponse {
    private List<Player> players;
}
