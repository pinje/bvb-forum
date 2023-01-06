package nl.fontys.s3.bvbforum.business.interfaces.rating;

import nl.fontys.s3.bvbforum.domain.PlayerAverageRatingDTO;
import nl.fontys.s3.bvbforum.domain.request.rating.GetRatingsByPositionRequest;
import nl.fontys.s3.bvbforum.domain.response.rating.GetAverageRatingsResponse;
import nl.fontys.s3.bvbforum.domain.response.rating.GetRatingsResponse;
import nl.fontys.s3.bvbforum.persistence.entity.PositionEnum;

import java.util.List;

public interface GetRatingsUseCase {
    GetRatingsResponse getRatings();
    GetRatingsResponse getRatingsByPlayerId(long playerId);
    GetRatingsResponse getRatingsByUserId(long userId);
    GetAverageRatingsResponse getAverageRatings();
    GetAverageRatingsResponse getAverageRatingsByPosition(GetRatingsByPositionRequest request);
}
