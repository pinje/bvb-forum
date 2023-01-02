package nl.fontys.s3.bvbforum.business.interfaces.player;

import nl.fontys.s3.bvbforum.domain.request.player.CreatePlayerRequest;
import nl.fontys.s3.bvbforum.domain.response.player.CreatePlayerResponse;

public interface CreatePlayerUseCase {
    CreatePlayerResponse createPlayer(CreatePlayerRequest request);
}
