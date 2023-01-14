package nl.fontys.s3.bvbforum.business.impl.comment;

import nl.fontys.s3.bvbforum.domain.request.comment.CreateCommentRequest;
import nl.fontys.s3.bvbforum.domain.response.comment.CreateCommentResponse;
import nl.fontys.s3.bvbforum.persistence.CommentRepository;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.CommentEntity;
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
public class CreateCommentUseCaseImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private CreateCommentUseCaseImpl createCommentUseCase;

    @Test
    void Add_ValidComment_CommentSavedInRepository() {
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

        CommentEntity commentEntity = CommentEntity.builder()
                .id(1L)
                .comment("comment")
                .user(userEntity)
                .post(postEntity)
                .build();

        CommentEntity requestCommentEntity = CommentEntity.builder()
                .comment("comment")
                .user(userEntity)
                .post(postEntity)
                .build();

        // set up mock objects
        when(commentRepository.save(requestCommentEntity)).thenReturn(commentEntity);
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userEntity));
        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(postEntity));

        // call the method
        CreateCommentRequest request = CreateCommentRequest.builder()
                .comment(commentEntity.getComment())
                .userId(1L)
                .postId(1L)
                .build();

        // when
        CreateCommentResponse response = createCommentUseCase.createComment(request);

        // then
        assertNotNull(response.getCommentId());

        // verify
        verify(commentRepository, times(1)).save(requestCommentEntity);
        verify(postRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
    }
}
