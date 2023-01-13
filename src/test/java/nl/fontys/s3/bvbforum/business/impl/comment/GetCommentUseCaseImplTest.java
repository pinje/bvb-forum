package nl.fontys.s3.bvbforum.business.impl.comment;

import nl.fontys.s3.bvbforum.domain.CommentInformationDTO;
import nl.fontys.s3.bvbforum.persistence.CommentRepository;
import nl.fontys.s3.bvbforum.persistence.entity.CommentEntity;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetCommentUseCaseImplTest {
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private GetCommentUseCaseImpl getCommentUseCase;

    @Test
    void Get_CommentById_ReturnsComment() {
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
                .id(456L)
                .comment("comment")
                .user(userEntity)
                .post(postEntity)
                .build();

        CommentInformationDTO expectedComment = CommentInformationDTO.builder()
                .id(456L)
                .comment("comment")
                .userId(userEntity.getId().toString())
                .username(userEntity.getUsername())
                .build();

        // set up mock objects
        when(commentRepository.findById(456L)).thenReturn(Optional.ofNullable(commentEntity));

        // when
        CommentInformationDTO actualComment = getCommentUseCase.getCommentById(456L);

        // then
        assertNotNull(actualComment);
        assertEquals(expectedComment, actualComment);

        // verify
        verify(commentRepository, times(1)).findById(456L);
    }

    @Test
    void Get_CommentById_CommentNotFound() {
        // given
        long nonExistentCommentId = -1;

        // set up mock objects
        when(commentRepository.findById(nonExistentCommentId)).thenReturn(Optional.empty());

        // when
        CommentInformationDTO result = getCommentUseCase.getCommentById(nonExistentCommentId);

        // then
        assertNull(result);

        // verify
        verify(commentRepository, times(1)).findById(nonExistentCommentId);
    }
}
