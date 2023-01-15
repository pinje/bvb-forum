package nl.fontys.s3.bvbforum.business.interfaces.vote;

import nl.fontys.s3.bvbforum.domain.request.vote.GetVoteRequest;

public interface GetVoteUseCase {
    boolean checkUserAlreadyUpvoted(GetVoteRequest request);
    boolean checkUserAlreadyDownvoted(GetVoteRequest request);
}
