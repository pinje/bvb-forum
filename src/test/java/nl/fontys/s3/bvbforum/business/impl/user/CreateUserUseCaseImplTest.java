package nl.fontys.s3.bvbforum.business.impl.user;

import nl.fontys.s3.bvbforum.business.exception.user.UserUsernameAlreadyExistsException;
import nl.fontys.s3.bvbforum.domain.request.user.CreateUserRequest;
import nl.fontys.s3.bvbforum.domain.response.user.CreateUserResponse;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void Add_ValidUser_UserSavedInRepository() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123456")
                .build();

        // set up mock objects
        when(userRepository.existsByUsername("Shuhei")).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(passwordEncoder.encode(userEntity.getPassword())).thenReturn("encoded_password");

        // call the method
        CreateUserRequest request = CreateUserRequest.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();

        // when
        CreateUserResponse response = createUserUseCase.createUser(request);

        // then
        assertNotNull(response.getUserId());

        // verify
        verify(userRepository, times(1)).existsByUsername("Shuhei");
        verify(passwordEncoder, times(1)).encode("123456");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void Add_UserWithExistingUsername_ThrowsException() throws UserUsernameAlreadyExistsException {
        // given
        CreateUserRequest request = CreateUserRequest.builder()
                .username("Shuhei")
                .password("123")
                .build();

        // set up mock objects
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new UserUsernameAlreadyExistsException());

        // when
        ResponseStatusException exception = assertThrows(UserUsernameAlreadyExistsException.class, () -> {
            createUserUseCase.createUser(request);
        });

        // then
        assertEquals("USERNAME_EXISTS", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(passwordEncoder, times(1)).encode(request.getPassword());
    }

}