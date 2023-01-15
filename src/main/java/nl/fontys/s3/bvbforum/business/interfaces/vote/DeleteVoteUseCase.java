package nl.fontys.s3.bvbforum.business.interfaces.vote;

import nl.fontys.s3.bvbforum.domain.request.vote.DeleteVoteRequest;

public interface DeleteVoteUseCase {
    void deleteVote(DeleteVoteRequest request);
}
