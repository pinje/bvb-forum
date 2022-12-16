package nl.fontys.s3.bvbforum.business.impl.user;

import nl.fontys.s3.bvbforum.business.exception.UnauthorizedDataAccessException;
import nl.fontys.s3.bvbforum.business.exception.user.UserDoesntExistException;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.domain.request.user.UpdateUserRequest;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.RoleEnum;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import nl.fontys.s3.bvbforum.persistence.entity.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;
    @Mock
    private AccessToken accessToken;

    @Test
    void Update_NonExistingUser_AdminRole_ThrowsException() throws UserDoesntExistException {
        // given
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(111L)
                .username("Shuhei")
                .password("123")
                .build();

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(true);
        when(userRepository.findById(111L)).thenThrow(new UserDoesntExistException());

        // when
        ResponseStatusException exception = assertThrows(UserDoesntExistException.class, () -> {
            updateUserUseCase.updateUser(request);
        });

        // then
        assertEquals("USER_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(userRepository, times(1)).findById(111L);
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
    }

    @Test
    void Update_NonExistingUser_NonAdminRole_ThrowsException() throws UserDoesntExistException {
        // given
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(111L)
                .username("Shuhei")
                .password("123")
                .build();

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(request.getId());
        when(userRepository.findById(111L)).thenThrow(new UserDoesntExistException());

        // when
        ResponseStatusException exception = assertThrows(UserDoesntExistException.class, () -> {
            updateUserUseCase.updateUser(request);
        });

        // then
        assertEquals("USER_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(userRepository, times(1)).findById(111L);
        verify(accessToken, times(1)).getUserId();
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
    }

    @Test
    void Update_ExistingUser_NonAdminRoleSameUser() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(111L)
                .username("Shuhei")
                .password("123")
                .build();

        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(111L)
                .username("username_changed")
                .password("password_changed")
                .build();

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(userEntity.getId());
        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.ofNullable(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        // when
        updateUserUseCase.updateUser(request);

        // then
        assertEquals("username_changed", userEntity.getUsername());
        assertEquals("password_changed", userEntity.getPassword());

        // verify
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
        verify(userRepository,times(1)).findById(111L);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void Update_ExistingUser_NonAdminRoleDifferentUser_ThrowsException() throws UnauthorizedDataAccessException {
        // given
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(111L)
                .username("username_changed")
                .password("password_changed")
                .build();

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(222L);

        // when
        ResponseStatusException exception = assertThrows(UnauthorizedDataAccessException.class, () -> {
            updateUserUseCase.updateUser(request);
        });

        // then
        assertEquals("USER_ID_NOT_FROM_LOGGED_IN_USER", exception.getReason());
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        // verify
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
    }
}