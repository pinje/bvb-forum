package nl.fontys.s3.bvbforum.business.impl.vote;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.vote.GetVoteUseCase;
import nl.fontys.s3.bvbforum.domain.VoteInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.vote.GetVoteRequest;
import nl.fontys.s3.bvbforum.persistence.VoteRepository;
import nl.fontys.s3.bvbforum.persistence.entity.VoteEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetVoteUseCaseImpl implements GetVoteUseCase {
    private VoteRepository voteRepository;

    @Override
    public VoteInformationDTO getVote(GetVoteRequest request) {
        List<VoteEntity> list;
        list = voteRepository.findAllByPostId(request.getPost());

        Optional<VoteEntity> result = Optional.empty();

        // check for each in list
        for (VoteEntity element : list) {
            if (Objects.equals(element.getUser().getId(), request.getUser())) {
                result = Optional.of(element);
            }
        }

        return result.stream().map(VoteConverter::convert).findFirst().orElse(null);
    }

    @Override
    public boolean checkUserAlreadyVoted(GetVoteRequest request) {
        List<VoteEntity> list;
        list = voteRepository.findAllByPostId(request.getPost());

        // check for each in list
        for (VoteEntity element : list) {
            if (Objects.equals(element.getUser().getId(), request.getUser())) {
                // already voted
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean checkUserAlreadyUpvoted(GetVoteRequest request) {
        List<VoteEntity> list;
        list = voteRepository.findAllByPostId(request.getPost());

        // check for each in list
        for (VoteEntity element : list) {
            if (Objects.equals(element.getUser().getId(), request.getUser())) {
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
            if (Objects.equals(element.getUser().getId(), request.getUser())) {
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
