package nl.fontys.s3.bvbforum.business.interfaces.player;

import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;

public interface GetPlayerUseCase {
    PlayerEntity getPlayerById(long playerId);
}
