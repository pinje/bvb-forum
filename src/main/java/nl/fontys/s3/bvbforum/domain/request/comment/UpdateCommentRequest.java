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
public class UpdateCommentRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String comment;
}
