package nl.fontys.s3.bvbforum.business.impl.vote;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.vote.CreateVoteUseCase;
import nl.fontys.s3.bvbforum.domain.request.vote.CreateVoteRequest;
import nl.fontys.s3.bvbforum.domain.response.vote.CreateVoteResponse;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.VoteRepository;
import nl.fontys.s3.bvbforum.persistence.entity.VoteEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CreateVoteUseCaseImpl implements CreateVoteUseCase {
    private VoteRepository voteRepository;
    private UserRepository userRepository;

    private PostRepository postRepository;
    @Transactional
    @Override
    public CreateVoteResponse createVote(CreateVoteRequest request) {
        VoteEntity saveVote = save(request);

        // ADD EXCEPTION

        return CreateVoteResponse.builder()
                .voteId(saveVote.getId())
                .build();
    }

    private VoteEntity save(CreateVoteRequest request) {
        VoteEntity newVote = VoteEntity.builder()
                .type(request.getType())
                .user(userRepository.findById(request.getUser())
                        .stream()
                        .findFirst().orElse(null))
                .post(postRepository.findById(request.getPost())
                        .stream()
                        .findFirst().orElse(null))
                .build();

        return voteRepository.save(newVote);
    }
}
