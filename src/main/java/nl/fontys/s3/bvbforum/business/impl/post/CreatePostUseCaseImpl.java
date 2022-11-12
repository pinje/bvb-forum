package nl.fontys.s3.bvbforum.business.impl.post;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.post.CreatePostUseCase;
import nl.fontys.s3.bvbforum.domain.request.post.CreatePostRequest;
import nl.fontys.s3.bvbforum.domain.response.post.CreatePostResponse;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CreatePostUseCaseImpl implements CreatePostUseCase {
    private PostRepository postRepository;

    @Transactional
    @Override
    public CreatePostResponse createPost(CreatePostRequest request) {
        PostEntity savePost = save(request);

        return CreatePostResponse.builder()
                .postId(savePost.getId())
                .build();
    }

    private PostEntity save(CreatePostRequest request) {
        PostEntity newPost = PostEntity.builder()
                .date(request.getDate())
                .title(request.getTitle())
                .content(request.getContent())
                .user(request.getUser())
                .build();

        return postRepository.save(newPost);
    }
}
