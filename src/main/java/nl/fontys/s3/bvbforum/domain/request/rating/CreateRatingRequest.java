package nl.fontys.s3.bvbforum.domain.request.rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRatingRequest {
    @NotNull
    private Long playerId;
    @NotNull
    private Long rating;
    @NotNull
    private Long userId;
    @NotNull
    private Long ratingPostId;
}
