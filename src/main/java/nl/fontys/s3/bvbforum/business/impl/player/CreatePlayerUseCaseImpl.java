package nl.fontys.s3.bvbforum.business.impl.player;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.player.CreatePlayerUseCase;
import nl.fontys.s3.bvbforum.domain.request.player.CreatePlayerRequest;
import nl.fontys.s3.bvbforum.domain.response.player.CreatePlayerResponse;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import nl.fontys.s3.bvbforum.persistence.entity.PositionEnum;
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
        PositionEnum position;

        if (request.getPosition() == "FW") {
            position = PositionEnum.FW;
        } else if (request.getPosition() == "MF") {
            position = PositionEnum.MF;
        } else if (request.getPosition() == "DF") {
            position = PositionEnum.DF;
        } else {
            position = PositionEnum.GK;
        }

        PlayerEntity newPlayer = PlayerEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .position(position)
                .build();

        return playerRepository.save(newPlayer);
    }
}
