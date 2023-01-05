package nl.fontys.s3.bvbforum.business.interfaces.rating_post;

import nl.fontys.s3.bvbforum.domain.request.rating_post.CreateRatingPostRequest;
import nl.fontys.s3.bvbforum.domain.response.rating_post.CreateRatingPostResponse;

public interface CreateRatingPostUseCase {
    CreateRatingPostResponse createRatingPost(CreateRatingPostRequest request);
}
