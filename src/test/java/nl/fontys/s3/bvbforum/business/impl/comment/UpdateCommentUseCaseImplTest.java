package nl.fontys.s3.bvbforum.business.impl.comment;

import nl.fontys.s3.bvbforum.business.exception.UnauthorizedDataAccessException;
import nl.fontys.s3.bvbforum.business.exception.comment.CommentDoesntExistException;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.domain.request.comment.UpdateCommentRequest;
import nl.fontys.s3.bvbforum.persistence.CommentRepository;
import nl.fontys.s3.bvbforum.persistence.entity.CommentEntity;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.RoleEnum;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCommentUseCaseImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private AccessToken accessToken;
    @InjectMocks
    private UpdateCommentUseCaseImpl updateCommentUseCase;

    @Test
    void Update_NonExistingComment_AdminRole_ThrowsException() throws CommentDoesntExistException {
        // given
        UpdateCommentRequest request = UpdateCommentRequest.builder()
                .id(1L)
                .comment("new_comment")
                .build();

        // set up mock objects
        when(commentRepository.findById(1L)).thenThrow(new CommentDoesntExistException());

        // when
        ResponseStatusException exception = assertThrows(CommentDoesntExistException.class, () -> updateCommentUseCase.updateComment(request));

        // then
        assertEquals("COMMENT_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void Update_ExistingComment_AdminRole() {
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

        UpdateCommentRequest request = UpdateCommentRequest.builder()
                .id(1L)
                .comment("comment_changed")
                .build();

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(true);
        when(commentRepository.findById(commentEntity.getId())).thenReturn(Optional.of(commentEntity));
        when(commentRepository.save(commentEntity)).thenReturn(commentEntity);

        // when
        updateCommentUseCase.updateComment(request);

        // then
        assertEquals("comment_changed", commentEntity.getComment());

        // verify
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(commentRepository, times(1)).findById(commentEntity.getId());
        verify(commentRepository, times(1)).save(commentEntity);
    }

    @Test
    void Update_ExistingComment_NonAdminRoleSameUser() {
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

        UpdateCommentRequest request = UpdateCommentRequest.builder()
                .id(1L)
                .comment("comment_changed")
                .build();

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(commentEntity.getUser().getId());
        when(commentRepository.findById(commentEntity.getId())).thenReturn(Optional.of(commentEntity));
        when(commentRepository.save(commentEntity)).thenReturn(commentEntity);

        // when
        updateCommentUseCase.updateComment(request);

        // then
        assertEquals("comment_changed", commentEntity.getComment());

        // verify
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
        verify(commentRepository, times(1)).findById(commentEntity.getId());
        verify(commentRepository, times(1)).save(commentEntity);
    }

    @Test
    void Update_ExistingComment_NonAdminRoleDifferentUser_ThrowsException() throws UnauthorizedDataAccessException {
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

        UpdateCommentRequest request = UpdateCommentRequest.builder()
                .id(1L)
                .comment("comment_changed")
                .build();

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(commentRepository.findById(request.getId())).thenReturn(Optional.ofNullable(commentEntity));
        when(accessToken.getUserId()).thenReturn(222L);

        // when
        ResponseStatusException exception = assertThrows(UnauthorizedDataAccessException.class, () -> updateCommentUseCase.updateComment(request));

        // then
        assertEquals("USER_ID_NOT_FROM_LOGGED_IN_USER", exception.getReason());
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        // verify
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
        verify(commentRepository, times(1)).findById(request.getId());
    }
}
