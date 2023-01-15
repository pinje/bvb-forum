package nl.fontys.s3.bvbforum.business.impl.vote;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.vote.GetVoteUseCase;
import nl.fontys.s3.bvbforum.domain.request.vote.GetVoteRequest;
import nl.fontys.s3.bvbforum.persistence.VoteRepository;
import nl.fontys.s3.bvbforum.persistence.entity.VoteEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetVoteUseCaseImpl implements GetVoteUseCase {
    private VoteRepository voteRepository;

    @Override
    public boolean checkUserAlreadyUpvoted(GetVoteRequest request) {
        List<VoteEntity> list;
        list = voteRepository.findAllByPostId(request.getPost());

        // check for each in list
        for (VoteEntity element : list) {
            if (element.getUser().getId() == request.getUser()) {
                // already voted
                if (element.getType()) {
                    // already upvoted
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean checkUserAlreadyDownvoted(GetVoteRequest request) {
        List<VoteEntity> list;
        list = voteRepository.findAllByPostId(request.getPost());

        // check for each in list
        for (VoteEntity element : list) {
            if (element.getUser().getId() == request.getUser()) {
                // already voted
                if (!element.getType()) {
                    // already downvoted
                    return true;
                }
            }
        }

        return false;
    }
}
