package nl.fontys.s3.bvbforum.business.impl.post;

import nl.fontys.s3.bvbforum.business.exception.UnauthorizedDataAccessException;
import nl.fontys.s3.bvbforum.business.exception.post.PostDoesntExistException;
import nl.fontys.s3.bvbforum.business.exception.user.UserDoesntExistException;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.domain.request.post.UpdatePostRequest;
import nl.fontys.s3.bvbforum.domain.request.user.UpdateUserRequest;
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
class UpdatePostUseCaseImplTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private AccessToken accessToken;
    @InjectMocks
    private UpdatePostUseCaseImpl updatePostUseCase;

    @Test
    void Update_NonExistingPost_AdminRole_ThrowsException() throws PostDoesntExistException {
        // given
        UpdatePostRequest request = UpdatePostRequest.builder()
                .id(111L)
                .title("title")
                .content("content")
                .build();

        // set up mock objects
        when(postRepository.findById(111L)).thenThrow(new PostDoesntExistException());

        // when
        ResponseStatusException exception = assertThrows(PostDoesntExistException.class, () -> {
            updatePostUseCase.updatePost(request);
        });

        // then
        assertEquals("POST_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(postRepository, times(1)).findById(111L);
    }

    @Test
    void Update_ExistingPost_AdminRole() {
        // given
        PostEntity postEntity = PostEntity.builder()
                .id(111L)
                .title("title")
                .content("content")
                .build();

        UpdatePostRequest request = UpdatePostRequest.builder()
                .id(111L)
                .title("title_changed")
                .content("content_changed")
                .build();

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(true);
        when(postRepository.findById(postEntity.getId())).thenReturn(Optional.ofNullable(postEntity));
        when(postRepository.save(postEntity)).thenReturn(postEntity);

        // when
        updatePostUseCase.updatePost(request);

        // then
        assertEquals("title_changed", postEntity.getTitle());
        assertEquals("content_changed", postEntity.getContent());

        // verify
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(postRepository,times(1)).findById(postEntity.getId());
        verify(postRepository, times(1)).save(postEntity);
    }

    @Test
    void Update_ExistingPost_NonAdminRoleSameUser() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(7L)
                .username("Shuhei")
                .password("123456")
                .build();

        PostEntity postEntity = PostEntity.builder()
                .id(111L)
                .title("title")
                .content("content")
                .user(userEntity)
                .build();

        UpdatePostRequest request = UpdatePostRequest.builder()
                .id(111L)
                .title("title_changed")
                .content("content_changed")
                .build();

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(postEntity.getUser().getId());
        when(postRepository.findById(postEntity.getId())).thenReturn(Optional.ofNullable(postEntity));
        when(postRepository.save(postEntity)).thenReturn(postEntity);

        // when
        updatePostUseCase.updatePost(request);

        // then
        assertEquals("title_changed", postEntity.getTitle());
        assertEquals("content_changed", postEntity.getContent());

        // verify
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
        verify(postRepository,times(1)).findById(postEntity.getId());
        verify(postRepository, times(1)).save(postEntity);
    }

    @Test
    void Update_ExistingPost_NonAdminRoleDifferentUser_ThrowsException() throws UnauthorizedDataAccessException {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(7L)
                .username("Shuhei")
                .password("123456")
                .build();

        PostEntity postEntity = PostEntity.builder()
                .id(111L)
                .title("title")
                .content("content")
                .user(userEntity)
                .build();

        UpdatePostRequest request = UpdatePostRequest.builder()
                .id(111L)
                .title("title_changed")
                .content("content_changed")
                .build();

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(postRepository.findById(request.getId())).thenReturn(Optional.ofNullable(postEntity));
        when(accessToken.getUserId()).thenReturn(222L);

        // when
        ResponseStatusException exception = assertThrows(UnauthorizedDataAccessException.class, () -> {
            updatePostUseCase.updatePost(request);
        });

        // then
        assertEquals("USER_ID_NOT_FROM_LOGGED_IN_USER", exception.getReason());
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        // verify
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
        verify(postRepository, times(1)).findById(request.getId());
    }
}