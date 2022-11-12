package nl.fontys.s3.bvbforum.business.interfaces.vote;

import nl.fontys.s3.bvbforum.domain.request.vote.CreateVoteRequest;
import nl.fontys.s3.bvbforum.domain.response.vote.CreateVoteResponse;

public interface CreateVoteUseCase {
    CreateVoteResponse createVote(CreateVoteRequest request);
}
