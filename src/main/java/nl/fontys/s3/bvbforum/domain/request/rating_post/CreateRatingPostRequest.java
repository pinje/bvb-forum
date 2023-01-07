package nl.fontys.s3.bvbforum.domain.request.rating_post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.bvbforum.persistence.entity.TournamentEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRatingPostRequest {
    @NotBlank
    private String start_year;
    @NotBlank
    private String end_year;
    @NotBlank
    private String matchday;
    @NotBlank
    private String opponent;
    @NotNull
    private TournamentEnum tournament;
    @NotEmpty
    private List<String> playersId;
}
