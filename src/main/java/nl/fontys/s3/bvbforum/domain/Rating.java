package nl.fontys.s3.bvbforum.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private Long id;
    private PlayerEntity player;
    private Long rating;
    private UserEntity user;
}
