package nl.fontys.s3.bvbforum.persistence.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEntity {
    private Long id;
    private String username;
}
