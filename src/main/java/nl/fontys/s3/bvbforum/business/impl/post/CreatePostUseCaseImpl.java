package nl.fontys.s3.bvbforum.business.impl.post;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.post.CreatePostUseCase;
import nl.fontys.s3.bvbforum.domain.request.post.CreatePostRequest;
import nl.fontys.s3.bvbforum.domain.response.post.CreatePostResponse;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.VoteRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.VoteEntity;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class CreatePostUseCaseImpl implements CreatePostUseCase {
    private PostRepository postRepository;
    private UserRepository userRepository;
    private VoteRepository voteRepository;

    @Transactional
    @Override
    public CreatePostResponse createPost(CreatePostRequest request) {
        PostEntity savePost = save(request);

        DateTime date = DateTime.now();
        Timestamp ts = new Timestamp(date.toDateTime().getMillis());

        savePost.setDate(ts);

        voteInit(savePost.getId(), request.getUserId());

        return CreatePostResponse.builder()
                .postId(savePost.getId())
                .build();
    }

    private PostEntity save(CreatePostRequest request) {
        PostEntity newPost = PostEntity.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .vote(request.getVote())
                .user(userRepository.findById(request.getUserId())
                        .stream()
                        .findFirst().orElse(null))
                .build();

        return postRepository.save(newPost);
    }

    private void voteInit(Long postId, Long userId) {

        VoteEntity newVote = VoteEntity.builder()
                .type(true)
                .user(userRepository.findById(userId)
                        .stream()
                        .findFirst().orElse(null))
                .post(postRepository.findById(postId)
                        .stream()
                        .findFirst().orElse(null))
                .build();

        voteRepository.save(newVote);
    }
}
