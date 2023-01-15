package nl.fontys.s3.bvbforum.business.impl.login;

import nl.fontys.s3.bvbforum.business.exception.login.InvalidCredentialsException;
import nl.fontys.s3.bvbforum.business.interfaces.login.AccessTokenEncoder;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.domain.request.login.LoginRequest;
import nl.fontys.s3.bvbforum.domain.response.login.LoginResponse;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccessTokenEncoder accessTokenEncoder;
    @InjectMocks
    private LoginUseCaseImpl loginUseCase;

    @Test
    void Login_Success() {
        // given
        UserRoleEntity roles = UserRoleEntity.builder()
                .role(RoleEnum.ADMIN)
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123456")
                .userRoles(Set.of(roles))
                .build();

        AccessToken token = AccessToken.builder()
                .subject(userEntity.getUsername())
                .roles(List.of("ADMIN"))
                .userId(userEntity.getId())
                .build();

        String accessToken = "secrettoken";

        // set up mock objects
        when(userRepository.findByUsername("Shuhei")).thenReturn(userEntity);
        when(passwordEncoder.matches("123456", userEntity.getPassword())).thenReturn(true);
        when(accessTokenEncoder.encode(token)).thenReturn(accessToken);

        // call the method
        LoginRequest request = LoginRequest.builder()
                .username("Shuhei")
                .password("123456")
                .build();

        // when
        LoginResponse actualResult = loginUseCase.login(request);

        // then
        assertEquals(accessToken, actualResult.getAccessToken());

        // verify
        verify(userRepository, times(1)).findByUsername("Shuhei");
        verify(passwordEncoder, times(1)).matches("123456", userEntity.getPassword());
        verify(accessTokenEncoder, times(1)).encode(token);
    }

    @Test
    void Login_UserNotFound_ThrowsException() throws InvalidCredentialsException {
        // given
        String nonExistentUserUsername = "What";

        // set up mock objects
        when(userRepository.findByUsername(nonExistentUserUsername)).thenReturn(null);

        // call the method
        LoginRequest request = LoginRequest.builder()
                .username(nonExistentUserUsername)
                .password("123456")
                .build();

        // when
        ResponseStatusException exception = assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(request));

        // then
        assertEquals("INVALID_CREDENTIALS", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(userRepository, times(1)).findByUsername(nonExistentUserUsername);
    }

    @Test
    void Login_PasswordWrong_ThrowsException() throws InvalidCredentialsException {
        // given
        String wrongPassword = "123";

        UserRoleEntity roles = UserRoleEntity.builder()
                .role(RoleEnum.ADMIN)
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123456")
                .userRoles(Set.of(roles))
                .build();

        // set up mock objects
        when(userRepository.findByUsername("Shuhei")).thenReturn(userEntity);
        when(passwordEncoder.matches(wrongPassword, userEntity.getPassword())).thenReturn(false);

        // call the method
        LoginRequest request = LoginRequest.builder()
                .username("Shuhei")
                .password("123")
                .build();

        // when
        ResponseStatusException exception = assertThrows(InvalidCredentialsException.class, () -> loginUseCase.login(request));

        // then
        assertEquals("INVALID_CREDENTIALS", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(userRepository, times(1)).findByUsername("Shuhei");
        verify(passwordEncoder, times(1)).matches(wrongPassword, userEntity.getPassword());
    }
}