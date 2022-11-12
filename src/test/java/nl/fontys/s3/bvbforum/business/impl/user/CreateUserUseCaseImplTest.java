package nl.fontys.s3.bvbforum.business.impl.user;

import nl.fontys.s3.bvbforum.business.exception.UserUsernameAlreadyExistsException;
import nl.fontys.s3.bvbforum.domain.User;
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

    @Test
    void Add_ValidUser_UserSavedInRepository() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123")
                .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        CreateUserRequest request = CreateUserRequest.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();

        // when
        CreateUserResponse actualResult = createUserUseCase.createUser(request);

        // then
        User user = User.builder().id(1L).username("Shuhei").password("123").build();
        CreateUserResponse expectedResult = CreateUserResponse.builder()
                        .userId(user.getId())
                        .build();
        assertEquals(expectedResult, actualResult);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void Add_UserWithExistingUsername_ThrowsException() throws UserUsernameAlreadyExistsException {
        // given
        CreateUserRequest request = CreateUserRequest.builder()
                .username("Shuhei")
                .password("123")
                .build();

        when(userRepository.save(any(UserEntity.class))).thenThrow(new UserUsernameAlreadyExistsException());

        // when
        ResponseStatusException exception = assertThrows(UserUsernameAlreadyExistsException.class, () -> {
            createUserUseCase.createUser(request);
        });

        // then
        assertEquals("USERNAME_EXISTS", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

}