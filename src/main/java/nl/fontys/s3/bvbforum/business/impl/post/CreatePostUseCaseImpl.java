package nl.fontys.s3.bvbforum.business.impl.post;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.post.CreatePostUseCase;
import nl.fontys.s3.bvbforum.domain.request.post.CreatePostRequest;
import nl.fontys.s3.bvbforum.domain.response.post.CreatePostResponse;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class CreatePostUseCaseImpl implements CreatePostUseCase {
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Transactional
    @Override
    public CreatePostResponse createPost(CreatePostRequest request) {
        PostEntity savePost = save(request);

        return CreatePostResponse.builder()
                .postId(savePost.getId())
                .build();
    }

    private PostEntity save(CreatePostRequest request) {
        DateTime date = DateTime.now();
        Timestamp ts = new Timestamp(date.toDateTime().getMillis());
        PostEntity newPost = PostEntity.builder()
                .date(ts)
                .title(request.getTitle())
                .content(request.getContent())
                .vote(request.getVote())
                .user(userRepository.findById(request.getUserId())
                        .stream()
                        .findFirst().orElse(null))
                .build();

        return postRepository.save(newPost);
    }
}
