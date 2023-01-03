package nl.fontys.s3.bvbforum.business.interfaces.rating;

import nl.fontys.s3.bvbforum.domain.request.rating.GetRatingsRequest;
import nl.fontys.s3.bvbforum.domain.response.rating.GetRatingsResponse;

public interface GetRatingsUseCase {
    GetRatingsResponse getRatings();
    GetRatingsResponse getRatingsByPlayerId(GetRatingsRequest request);
    GetRatingsResponse getRatingsByUserId(GetRatingsRequest request);
}
