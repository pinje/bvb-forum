package nl.fontys.s3.bvbforum.business.impl.comment;

import nl.fontys.s3.bvbforum.business.exception.UnauthorizedDataAccessException;
import nl.fontys.s3.bvbforum.business.exception.comment.CommentDoesntExistException;
import nl.fontys.s3.bvbforum.domain.AccessToken;
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
public class DeleteCommentUseCaseImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private AccessToken accessToken;
    @InjectMocks
    private DeleteCommentUseCaseImpl deleteCommentUseCase;

    @Test
    void Delete_ExistingComment_AdminRole() {
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

        // set up mock objects
        when(commentRepository.findById(commentEntity.getId())).thenReturn(Optional.of(commentEntity));
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(true);

        // when
        deleteCommentUseCase.deleteComment(commentEntity.getId());

        // verify
        verify(commentRepository, times(1)).deleteById(1L);
        verify(commentRepository, times(1)).findById(commentEntity.getId());
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
    }

    @Test
    void Delete_ExistingComment_NonAdminRoleSameUser() {
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

        // set up mock objects
        when(commentRepository.findById(commentEntity.getId())).thenReturn(Optional.of(commentEntity));
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(commentEntity.getUser().getId());

        // when
        deleteCommentUseCase.deleteComment(commentEntity.getId());

        // verify
        verify(commentRepository, times(1)).findById(commentEntity.getId());
        verify(commentRepository, times(1)).deleteById(commentEntity.getId());
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
    }

    @Test
    void Delete_ExistingComment_NonAdminRoleDifferentUser_ThrowsException() throws UnauthorizedDataAccessException {
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

        // set up mock objects
        when(commentRepository.findById(commentEntity.getId())).thenReturn(Optional.of(commentEntity));
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(222L);

        // when
        ResponseStatusException exception = assertThrows(UnauthorizedDataAccessException.class, () -> deleteCommentUseCase.deleteComment(commentEntity.getId()));

        // then
        assertEquals("USER_ID_NOT_FROM_LOGGED_IN_USER", exception.getReason());
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        // verify
        verify(commentRepository, times(1)).findById(commentEntity.getId());
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
    }

    @Test
    void Delete_NonExistingComment_ThrowsException() throws CommentDoesntExistException {
        // given
        long nonExistentCommentId = -1;


        // set up mock objects
        when(commentRepository.findById(nonExistentCommentId)).thenReturn(Optional.empty());

        // when
        ResponseStatusException exception = assertThrows(CommentDoesntExistException.class, () -> deleteCommentUseCase.deleteComment(nonExistentCommentId));

        // then
        assertEquals("COMMENT_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(commentRepository, times(1)).findById(nonExistentCommentId);
    }
}
