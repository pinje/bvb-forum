package nl.fontys.s3.bvbforum.business.impl.rating;

import nl.fontys.s3.bvbforum.domain.Rating;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;

final class RatingConverter {

    private RatingConverter() {

    }

    public static Rating convert(RatingEntity rating) {
        return Rating.builder()
                .id(rating.getId())
                .player(rating.getPlayer())
                .rating(rating.getRating())
                .user(rating.getUser())
                .build();
    }
}
