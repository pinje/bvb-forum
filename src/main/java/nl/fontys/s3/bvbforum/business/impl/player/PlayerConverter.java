package nl.fontys.s3.bvbforum.business.impl.player;

import nl.fontys.s3.bvbforum.domain.Player;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;

final class PlayerConverter {

    private PlayerConverter() {

    }

    public static Player convert(PlayerEntity player) {
        return Player.builder()
                .id(player.getId())
                .firstname(player.getFirstname())
                .lastname(player.getLastname())
                .position(player.getPosition())
                .build();
    }
}
