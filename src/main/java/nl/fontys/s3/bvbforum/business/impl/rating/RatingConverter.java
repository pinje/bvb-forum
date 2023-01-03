package nl.fontys.s3.bvbforum.business.impl.rating;

import nl.fontys.s3.bvbforum.domain.RatingInformationDTO;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;

final class RatingConverter {

    private RatingConverter() {

    }

    public static RatingInformationDTO convert(RatingEntity rating) {
        return RatingInformationDTO.builder()
                .id(rating.getId())
                .player(rating.getPlayer())
                .rating(rating.getRating())
                .userId(rating.getUser().getId())
                .build();
    }
}
