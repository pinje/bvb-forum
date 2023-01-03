package nl.fontys.s3.bvbforum.business.interfaces.rating;

import nl.fontys.s3.bvbforum.domain.response.rating.GetRatingsResponse;

public interface GetRatingsUseCase {
    GetRatingsResponse getRatings();
    GetRatingsResponse getRatingsByPlayerId(long playerId);
    GetRatingsResponse getRatingsByUserId(long userId);
}
