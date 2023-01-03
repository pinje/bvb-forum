package nl.fontys.s3.bvbforum.business.interfaces.rating;

import nl.fontys.s3.bvbforum.domain.request.rating.CreateRatingRequest;
import nl.fontys.s3.bvbforum.domain.response.rating.CreateRatingResponse;

public interface CreateRatingUseCase {
    CreateRatingResponse createRating(CreateRatingRequest request);
}
