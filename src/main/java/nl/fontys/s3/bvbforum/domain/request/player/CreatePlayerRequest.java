package nl.fontys.s3.bvbforum.domain.request.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.bvbforum.persistence.entity.PositionEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlayerRequest {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String position;
}
