package nl.fontys.s3.bvbforum.domain.response.rating;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRatingResponse {
    private Long ratingId;
}
