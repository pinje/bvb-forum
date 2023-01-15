package nl.fontys.s3.bvbforum.business.interfaces.vote;

import nl.fontys.s3.bvbforum.domain.VoteInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.vote.GetVoteRequest;
import nl.fontys.s3.bvbforum.persistence.entity.VoteEntity;

public interface GetVoteUseCase {
    VoteInformationDTO getVote(GetVoteRequest request);
    boolean checkUserAlreadyVoted(GetVoteRequest request);
    boolean checkUserAlreadyUpvoted(GetVoteRequest request);
    boolean checkUserAlreadyDownvoted(GetVoteRequest request);
}
