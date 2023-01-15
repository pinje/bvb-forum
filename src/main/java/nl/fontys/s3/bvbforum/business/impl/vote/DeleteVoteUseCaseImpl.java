package nl.fontys.s3.bvbforum.business.impl.vote;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.vote.DeleteVoteUseCase;
import nl.fontys.s3.bvbforum.domain.request.vote.DeleteVoteRequest;
import nl.fontys.s3.bvbforum.persistence.VoteRepository;
import nl.fontys.s3.bvbforum.persistence.entity.VoteEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteVoteUseCaseImpl implements DeleteVoteUseCase {

    private final VoteRepository voteRepository;

    @Override
    public void deleteVote(DeleteVoteRequest request) {
        List<VoteEntity> list;
        list = voteRepository.findAllByPostId(request.getPost());
        long id = 0;

        // check for each in list
        for (VoteEntity element : list) {
            if (Objects.equals(element.getUser().getId(), request.getUser())) {
                id = element.getId();
            }
        }

        this.voteRepository.deleteById(id);
    }
}
