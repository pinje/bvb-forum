package nl.fontys.s3.bvbforum.business.interfaces.rating;

import nl.fontys.s3.bvbforum.domain.PlayerAverageRatingDTO;
import nl.fontys.s3.bvbforum.domain.RatingInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.rating.GetRatingRequest;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;

public interface GetRatingUseCase {
    RatingInformationDTO getRatingById(long ratingId);
    PlayerAverageRatingDTO getAverageRatingByPlayerId(long playerId);
    boolean checkUserAlreadyVoted(GetRatingRequest request);
}
