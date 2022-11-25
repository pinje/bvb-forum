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
public class PostInformationDTO {
    private Long id;
    private Timestamp date;
    private String title;
    private String content;
    private Long vote;
    private String userId;
    private String username;
}
