package nl.fontys.s3.bvbforum.business.impl.player;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.player.GetPlayerUseCase;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetPlayerUseCaseImpl implements GetPlayerUseCase {
    private PlayerRepository playerRepository;

    @Override
    public PlayerEntity getPlayerById(long playerId) {
        return playerRepository.findById(playerId)
                .stream()
                .findFirst()
                .orElse(null);
    }
}
