package nl.fontys.s3.bvbforum.business.impl.post;

import nl.fontys.s3.bvbforum.business.exception.UnauthorizedDataAccessException;
import nl.fontys.s3.bvbforum.business.exception.post.PostDoesntExistException;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePostUseCaseImplTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private AccessToken accessToken;
    @InjectMocks
    private DeletePostUseCaseImpl deletePostUseCase;

    @Test
    void Delete_ExistingPost_AdminRole() {
        // given
        PostEntity postEntity = PostEntity.builder()
                .id(1L)
                .title("title")
                .content("content")
                .build();

        // set up mock objects
        when(postRepository.findById(postEntity.getId())).thenReturn(Optional.of(postEntity));
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(true);

        // when
        deletePostUseCase.deletePost(postEntity.getId());

        // verify
        verify(postRepository, times(1)).deleteById(1L);
        verify(postRepository, times(1)).findById(postEntity.getId());
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
    }

    @Test
    void Delete_ExistingPost_NonAdminRoleSameUser() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(111L)
                .username("Shuhei")
                .password("123456")
                .build();

        PostEntity postEntity = PostEntity.builder()
                .id(111L)
                .title("title")
                .content("content")
                .user(userEntity)
                .build();

        // set up mock objects
        when(postRepository.findById(postEntity.getId())).thenReturn(Optional.of(postEntity));
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(postEntity.getUser().getId());

        // when
        deletePostUseCase.deletePost(postEntity.getId());

        //verify
        verify(postRepository, times(1)).findById(postEntity.getId());
        verify(postRepository, times(1)).deleteById(postEntity.getId());
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
    }

    @Test
    void Delete_ExistingPost_NonAdminRoleDifferentUser_ThrowsException() throws UnauthorizedDataAccessException {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(111L)
                .username("Shuhei")
                .password("123456")
                .build();

        PostEntity postEntity = PostEntity.builder()
                .id(111L)
                .title("title")
                .content("content")
                .user(userEntity)
                .build();

        // set up mock objects
        when(postRepository.findById(postEntity.getId())).thenReturn(Optional.of(postEntity));
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(222L);

        // when
        ResponseStatusException exception = assertThrows(UnauthorizedDataAccessException.class, () -> deletePostUseCase.deletePost(postEntity.getId()));

        // then
        assertEquals("USER_ID_NOT_FROM_LOGGED_IN_USER", exception.getReason());
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        // verify
        verify(postRepository, times(1)).findById(postEntity.getId());
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
    }

    @Test
    void Delete_NonExistingPost_ThrowsException() throws PostDoesntExistException {
        // given
        long nonExistentPostId = -1;

        // set up mock objects
        when(postRepository.findById(nonExistentPostId)).thenReturn(Optional.empty());

        // when
        ResponseStatusException exception = assertThrows(PostDoesntExistException.class, () -> deletePostUseCase.deletePost(nonExistentPostId));

        // then
        assertEquals("POST_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(postRepository, times(1)).findById(nonExistentPostId);
    }

}