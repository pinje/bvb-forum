package nl.fontys.s3.bvbforum.domain.response.rating_post;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRatingPostResponse {
    private Long ratingPostId;
}
