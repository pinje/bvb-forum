package nl.fontys.s3.bvbforum.business.impl.player;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.player.PlayerDoesntExistException;
import nl.fontys.s3.bvbforum.business.exception.rating_post.RatingPostDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.player.GetPlayersUseCase;
import nl.fontys.s3.bvbforum.domain.Player;
import nl.fontys.s3.bvbforum.domain.response.player.GetPlayersResponse;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingPostPlayerRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import nl.fontys.s3.bvbforum.persistence.entity.RatingPostPlayerEntity;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetPlayersUseCaseImpl implements GetPlayersUseCase {
    private PlayerRepository playerRepository;
    private RatingPostPlayerRepository ratingPostPlayerRepository;

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

    @Override
    public GetPlayersResponse getPlayersByRatingPostId(long ratingPostId) {
        List<RatingPostPlayerEntity> ratingPostPlayerEntities = ratingPostPlayerRepository.findRatingPostPlayerEntitiesByRatingPostId(ratingPostId);

        if (ratingPostPlayerEntities.isEmpty()) {
            throw new RatingPostDoesntExistException();
        }

        List<Player> players = ratingPostPlayerEntities.stream()
                .map(ratingPostPlayerEntity -> ratingPostPlayerEntity.getPlayer())
                .map(PlayerConverter::convert)
                .collect(Collectors.toList());

        return GetPlayersResponse.builder()
                .players(players)
                .build();
    }
}
