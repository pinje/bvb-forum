package nl.fontys.s3.bvbforum.business.interfaces.rating;

import nl.fontys.s3.bvbforum.domain.PlayerAverageRatingDTO;
import nl.fontys.s3.bvbforum.domain.Rating;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;

public interface GetRatingUseCase {
    RatingEntity getRatingById(long ratingId);
    PlayerAverageRatingDTO getAverageRatingByPlayerId(long playerId);
}
