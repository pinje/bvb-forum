package nl.fontys.s3.bvbforum.domain.response.post;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePostResponse {
    private Long postId;
}
