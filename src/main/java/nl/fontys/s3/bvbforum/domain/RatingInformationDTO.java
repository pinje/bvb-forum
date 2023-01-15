package nl.fontys.s3.bvbforum.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingInformationDTO {
    private Long id;
    private PlayerEntity player;
    private Long rating;
    private Long userId;
}
