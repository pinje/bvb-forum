package nl.fontys.s3.bvbforum.business.interfaces.vote;

import nl.fontys.s3.bvbforum.domain.request.vote.UpdateVoteRequest;

public interface UpdateVoteUseCase {
    void updateVote(UpdateVoteRequest request);
}
