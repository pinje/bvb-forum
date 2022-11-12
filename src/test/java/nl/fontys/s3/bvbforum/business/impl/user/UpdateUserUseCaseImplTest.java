package nl.fontys.s3.bvbforum.business.impl.user;

import nl.fontys.s3.bvbforum.business.exception.UserDoesntExistException;
import nl.fontys.s3.bvbforum.domain.request.user.UpdateUserRequest;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    @Test
    void Update_ValidUserUsername_UsernameChanged() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(111L)
                .username("Shuhei")
                .password("123")
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(111L)
                .username("Fontys")
                .password("123")
                .build();

        // when
        updateUserUseCase.updateUser(request);

        // then
        assertEquals("Fontys", userEntity.getUsername());
        assertEquals("123", userEntity.getPassword());
        verify(userRepository,times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void Update_ValidUserUsername_PasswordChanged() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(111L)
                .username("Shuhei")
                .password("123")
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(111L)
                .username("Shuhei")
                .password("321")
                .build();

        // when
        updateUserUseCase.updateUser(request);

        // then
        assertEquals("Shuhei", userEntity.getUsername());
        assertEquals("321", userEntity.getPassword());
        verify(userRepository,times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void Update_NoExistingUser_ThrowsException() throws UserDoesntExistException {
        // given
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(111L)
                .username("Fontys")
                .password("123")
                .build();

        when(userRepository.findById(anyLong())).thenThrow(new UserDoesntExistException());

        // when
        ResponseStatusException exception = assertThrows(UserDoesntExistException.class, () -> {
            updateUserUseCase.updateUser(request);
        });

        // then
        assertEquals("USER_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(userRepository, times(1)).findById(anyLong());
    }

}