package nl.fontys.s3.bvbforum.domain.request.post;

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
public class CreatePostRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private Long vote;
    @NotNull
    private Long userId;
}
