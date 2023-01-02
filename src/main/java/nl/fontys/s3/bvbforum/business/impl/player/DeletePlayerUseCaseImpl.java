package nl.fontys.s3.bvbforum.business.impl.player;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.player.PlayerDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.player.DeletePlayerUseCase;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeletePlayerUseCaseImpl implements DeletePlayerUseCase {
    private final PlayerRepository playerRepository;

    @Override
    public void deletePlayer(long playerId) {
        Optional<PlayerEntity> playerOptional = playerRepository.findById(playerId);

        if (playerOptional.isEmpty()) {
            throw new PlayerDoesntExistException();
        }

        this.playerRepository.deleteById(playerId);
    }
}
