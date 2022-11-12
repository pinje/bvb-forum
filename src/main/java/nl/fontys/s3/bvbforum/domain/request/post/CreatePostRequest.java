package nl.fontys.s3.bvbforum.domain.request.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.joda.time.DateTime;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {
    @NotBlank
    private Timestamp date;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private UserEntity user;
}
