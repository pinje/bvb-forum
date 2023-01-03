package nl.fontys.s3.bvbforum.business.impl.rating;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.rating.GetRatingUseCase;
import nl.fontys.s3.bvbforum.domain.PlayerAverageRatingDTO;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingRepository;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetRatingUseCaseImpl implements GetRatingUseCase {
    private RatingRepository ratingRepository;
    private PlayerRepository playerRepository;

    @Override
    public RatingEntity getRatingById(long ratingId) {
        return ratingRepository.findById(ratingId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public PlayerAverageRatingDTO getAverageRatingByPlayerId(long playerId) {
        List<RatingEntity> list;
        list = ratingRepository.findAllByPlayerId(playerId);

        double averageRating = list.stream()
                .mapToInt(ratingEntity -> Math.toIntExact(ratingEntity.getRating()))
                .average()
                .orElse(0.0);

        PlayerAverageRatingDTO result = PlayerAverageRatingDTO.builder()
                .player(playerRepository.findById(playerId)
                        .stream().findFirst().orElse(null))
                .averageRating(averageRating)
                .build();

        return result;
    }
}
