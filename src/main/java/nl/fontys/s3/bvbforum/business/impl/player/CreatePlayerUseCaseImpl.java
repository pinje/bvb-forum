package nl.fontys.s3.bvbforum.business.impl.player;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.player.CreatePlayerUseCase;
import nl.fontys.s3.bvbforum.domain.request.player.CreatePlayerRequest;
import nl.fontys.s3.bvbforum.domain.response.player.CreatePlayerResponse;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CreatePlayerUseCaseImpl implements CreatePlayerUseCase {
    private PlayerRepository playerRepository;

    @Transactional
    @Override
    public CreatePlayerResponse createPlayer(CreatePlayerRequest request) {
        PlayerEntity savePlayer = save(request);

        return CreatePlayerResponse.builder()
                .playerId(savePlayer.getId())
                .build();
    }

    private PlayerEntity save(CreatePlayerRequest request) {
        PlayerEntity newPlayer = PlayerEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .position(request.getPosition())
                .build();

        return playerRepository.save(newPlayer);
    }
}
