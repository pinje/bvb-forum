package nl.fontys.s3.bvbforum.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteInformationDTO {
    private Long id;
    private boolean type;
    private Long userId;
    private Long postId;
}
