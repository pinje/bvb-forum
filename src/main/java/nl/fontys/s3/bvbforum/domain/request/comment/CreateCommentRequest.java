package nl.fontys.s3.bvbforum.domain.request.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {
    @NotBlank
    private String comment;
    @NotNull
    private Long userId;
    @NotNull
    private Long postId;
}
