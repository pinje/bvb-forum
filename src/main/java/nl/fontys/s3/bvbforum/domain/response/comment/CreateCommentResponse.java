package nl.fontys.s3.bvbforum.domain.response.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCommentResponse {
    private Long commentId;
}
