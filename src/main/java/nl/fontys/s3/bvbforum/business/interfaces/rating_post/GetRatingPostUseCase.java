package nl.fontys.s3.bvbforum.business.interfaces.rating_post;

import nl.fontys.s3.bvbforum.persistence.entity.RatingPostEntity;

public interface GetRatingPostUseCase {
    RatingPostEntity getRatingPostById(long ratingPostId);
    RatingPostEntity getMostRecentRatingPost();
}
