package nl.fontys.s3.bvbforum.domain.response.vote;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateVoteResponse {
    private Long voteId;
}
