package nl.fontys.s3.bvbforum.domain.response.rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.bvbforum.domain.PlayerAverageRatingDTO;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAverageRatingsResponse {
    private List<PlayerAverageRatingDTO> averageRatings;
}
