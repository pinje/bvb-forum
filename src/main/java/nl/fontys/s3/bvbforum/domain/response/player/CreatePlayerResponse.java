package nl.fontys.s3.bvbforum.domain.response.player;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePlayerResponse {
    private Long playerId;
}
