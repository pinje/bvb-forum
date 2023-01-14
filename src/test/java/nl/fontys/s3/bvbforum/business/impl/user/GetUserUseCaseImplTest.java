package nl.fontys.s3.bvbforum.business.impl.user;

import nl.fontys.s3.bvbforum.business.exception.UnauthorizedDataAccessException;
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
class GetUserUseCaseImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private GetUserUseCaseImpl getUserUseCase;
    @Mock
    private AccessToken accessToken;

    @Test
    void Get_UserById_AdminRole_ReturnsUser() {
        // given
        UserEntity expectedUser = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .build();

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(true);
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));

        // when
        UserEntity actualUser = getUserUseCase.getUserById(1L);

        // then
        assertEquals(expectedUser, actualUser);

        // verify
        verify(userRepository, times(1)).findById(1L);
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
    }

    @Test
    void Get_UserById_NonAdminSameUser_ReturnsUser() {
        // given
        UserEntity expectedUser = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .build();

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(1L);
        when(userRepository.findById(expectedUser.getId())).thenReturn(Optional.of(expectedUser));

        // when
        UserEntity actualUser = getUserUseCase.getUserById(1L);

        // then
        assertEquals(expectedUser, actualUser);

        // verify
        verify(userRepository, times(1)).findById(1L);
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
    }

    @Test
    void Get_UserById_NonAdminDifferentUser_ThrowsException() throws UnauthorizedDataAccessException {
        // given

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(2L);

        // when
        ResponseStatusException exception = assertThrows(UnauthorizedDataAccessException.class, () -> getUserUseCase.getUserById(1L));

        // then
        assertEquals("USER_ID_NOT_FROM_LOGGED_IN_USER", exception.getReason());
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        // verify
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
    }

    @Test
    void Get_UserById_UserNotFound() {
        // given
        long nonExistentUserId = -1;

        // set up mock objects
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(true);
        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        // when
        UserEntity result = getUserUseCase.getUserById(nonExistentUserId);

        // then
        assertNull(result);

        // verify
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(userRepository, times(1)).findById(nonExistentUserId);
    }
}