package nl.fontys.s3.bvbforum.business.impl.post;

import nl.fontys.s3.bvbforum.domain.request.post.CreatePostRequest;
import nl.fontys.s3.bvbforum.domain.response.post.CreatePostResponse;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePostUseCaseImplTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CreatePostUseCaseImpl createPostUseCase;

    @Test
    void Add_ValidPost_PostSavedInRepository() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123456")
                .build();

        PostEntity postEntity = PostEntity.builder()
                .id(1L)
                .title("title")
                .content("content")
                .vote(1L)
                .user(userEntity)
                .build();

        PostEntity requestPostEntity = PostEntity.builder()
                .title("title")
                .content("content")
                .vote(1L)
                .user(userEntity)
                .build();

        // set up mock objects
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userEntity));
        when(postRepository.save(requestPostEntity)).thenReturn(postEntity);

        // call the method
        CreatePostRequest request = CreatePostRequest.builder()
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .vote(postEntity.getVote())
                .userId(1L)
                .build();

        // when
        CreatePostResponse response = createPostUseCase.createPost(request);

        // then
        assertNotNull(response.getPostId());

        // verify
        verify(postRepository, times(1)).save(requestPostEntity);
        verify(userRepository, times(1)).findById(1L);
    }
}