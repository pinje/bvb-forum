package nl.fontys.s3.bvbforum.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentInformationDTO {
    private Long id;
    private Timestamp date;
    private String comment;
    private String userId;
    private String username;
}
