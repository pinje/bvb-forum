package nl.fontys.s3.bvbforum.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    private Long id;
    private boolean type;
    private UserEntity user;
    private PostEntity post;
}
