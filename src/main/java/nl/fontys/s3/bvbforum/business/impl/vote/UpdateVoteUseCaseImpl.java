package nl.fontys.s3.bvbforum.business.impl.vote;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.post.PostDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.vote.UpdateVoteUseCase;
import nl.fontys.s3.bvbforum.domain.request.post.UpdatePostRequest;
import nl.fontys.s3.bvbforum.domain.request.vote.UpdateVoteRequest;
import nl.fontys.s3.bvbforum.persistence.VoteRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.VoteEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateVoteUseCaseImpl implements UpdateVoteUseCase {
    private VoteRepository voteRepository;

    @Override
    public void updateVote(UpdateVoteRequest request) {
        List<VoteEntity> list;
        list = voteRepository.findAllByPostId(request.getPost());
        long id = 0;

        // check for each in list
        for (VoteEntity element : list) {
            if (Objects.equals(element.getUser().getId(), request.getUser())) {
                id = element.getId();
            }
        }

        Optional<VoteEntity> voteOptional = voteRepository.findById(id);

        // ADD EXCEPTIONS

        VoteEntity post = voteOptional.get();
        updateFields(post);
    }

    private void updateFields(VoteEntity vote) {
        // if 0 then 1, if 1 then 0
        vote.setType(!vote.getType());
        voteRepository.save(vote);
    }
}
