package nl.fontys.s3.bvbforum.business.impl.user;

import nl.fontys.s3.bvbforum.business.exception.UnauthorizedDataAccessException;
import nl.fontys.s3.bvbforum.business.exception.user.UserDoesntExistException;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
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
class DeleteUserUseCaseImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccessToken accessToken;
    @InjectMocks
    private DeleteUserUseCaseImpl deleteUserUseCase;

    @Test
    void Delete_ExistingUser_AdminRole() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(111L)
                .username("Shuhei")
                .password("123456")
                .build();

        // set up mock objects
        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(true);

        // when
        deleteUserUseCase.deleteUser(userEntity.getId());

        // verify
        verify(userRepository, times(1)).findById(userEntity.getId());
        verify(userRepository, times(1)).deleteById(111L);
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
    }

    @Test
    void Delete_ExistingUser_NonAdminRoleSameUser() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(111L)
                .username("Shuhei")
                .password("123456")
                .build();

        // set up mock objects
        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(userEntity.getId());

        // when
        deleteUserUseCase.deleteUser(userEntity.getId());

        // verify
        verify(userRepository, times(1)).findById(userEntity.getId());
        verify(userRepository, times(1)).deleteById(userEntity.getId());
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();

    }

    @Test
    void Delete_ExistingUser_NonAdminRoleDifferentUser_ThrowsException() throws UnauthorizedDataAccessException {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(111L)
                .username("Shuhei")
                .password("123456")
                .build();

        Long id = userEntity.getId();

        // set up mock objects
        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(222L);

        // when
        ResponseStatusException exception = assertThrows(UnauthorizedDataAccessException.class, () -> {
            deleteUserUseCase.deleteUser(id);
        });

        // then
        assertEquals("USER_ID_NOT_FROM_LOGGED_IN_USER", exception.getReason());
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        // verify
        verify(userRepository, times(1)).findById(userEntity.getId());
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
    }

    @Test
    void Delete_NonExistingUser_ThrowsException() throws UserDoesntExistException {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(111L)
                .username("Shuhei")
                .build();

        Long id = userEntity.getId();

        // set up mock objects
        doThrow(new UserDoesntExistException()).when(userRepository).deleteById(111L);

        // when
        ResponseStatusException exception = assertThrows(UserDoesntExistException.class, () -> {
            deleteUserUseCase.deleteUser(id);
        });

        // then
        assertEquals("USER_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(userRepository, times(1)).deleteById(111L);
    }
}