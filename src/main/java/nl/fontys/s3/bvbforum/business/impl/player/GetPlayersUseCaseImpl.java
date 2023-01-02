package nl.fontys.s3.bvbforum.business.impl.player;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.player.PlayerDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.player.GetPlayersUseCase;
import nl.fontys.s3.bvbforum.domain.Player;
import nl.fontys.s3.bvbforum.domain.response.player.GetPlayersResponse;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class GetPlayersUseCaseImpl implements GetPlayersUseCase {
    private PlayerRepository playerRepository;

    @Transactional
    @Override
    public GetPlayersResponse getPlayers() {
        List<Player> players = playerRepository.findAll()
                .stream()
                .map(PlayerConverter::convert)
                .toList();

        if (players.isEmpty()) {
            throw new PlayerDoesntExistException();
        }

        return GetPlayersResponse.builder()
                .players(players)
                .build();
    }
}
