package nl.fontys.s3.bvbforum.domain.request.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetVoteRequest {
    @NotNull
    private Long user;
    @NotNull
    private Long post;
}
