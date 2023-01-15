package nl.fontys.s3.bvbforum.business.impl.rating;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.rating.GetRatingUseCase;
import nl.fontys.s3.bvbforum.domain.PlayerAverageRatingDTO;
import nl.fontys.s3.bvbforum.domain.RatingInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.rating.GetRatingRequest;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingRepository;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class GetRatingUseCaseImpl implements GetRatingUseCase {
    private RatingRepository ratingRepository;
    private PlayerRepository playerRepository;

    @Override
    public RatingInformationDTO getRatingById(long ratingId) {
        return ratingRepository.findById(ratingId)
                .stream()
                .map(RatingConverter::convert)
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
                .orElse(0.00);

        String formattedAverageRating = String.format("%.2f", averageRating);

        return PlayerAverageRatingDTO.builder()
                .player(playerRepository.findById(playerId)
                        .stream().findFirst().orElse(null))
                .averageRating(formattedAverageRating)
                .build();
    }

    @Override
    public boolean checkUserAlreadyVoted(GetRatingRequest request) {
        List<RatingEntity> list;
        list = ratingRepository.findAllByUserId(request.getUserId());

        // check for each in list
        for (RatingEntity element : list) {
            if (Objects.equals(element.getRatingPost().getId(), request.getRatingPostId())) {
                // already voted
                return true;
            }
        }

        return false;
    }
}
