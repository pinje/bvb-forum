package nl.fontys.s3.bvbforum.business.impl;

import net.bytebuddy.implementation.bytecode.Throw;
import nl.fontys.s3.bvbforum.business.exception.UserUsernameAlreadyExistsException;
import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.domain.request.CreateUserRequest;
import nl.fontys.s3.bvbforum.domain.response.CreateUserResponse;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    void CreateUser() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .build();

        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userEntity);

        CreateUserRequest request = CreateUserRequest.builder()
                .username(userEntity.getUsername())
                .build();

        // when
        CreateUserResponse actualResult = createUserUseCase.createUser(request);

        // then
        User user = User.builder().id(1L).username("Shuhei").build();
        CreateUserResponse expectedResult = CreateUserResponse.builder()
                        .userId(user.getId())
                        .build();
        assertEquals(expectedResult, actualResult);
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
    }

    @Test
    @Disabled
    void CreateUser_UsernameAlreadyExistsException() throws UserUsernameAlreadyExistsException {
        // given
        CreateUserRequest request = CreateUserRequest.builder()
                .username("Shuhei")
                .build();

        when(userRepositoryMock.save(any(UserEntity.class))).thenThrow(UserUsernameAlreadyExistsException.class);

        // when
        ResponseStatusException exception = assertThrows(UserUsernameAlreadyExistsException.class, () -> {
            createUserUseCase.createUser(request);
        });


        // then
        assertEquals("USERNAME_EXISTS", exception.getMessage());
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
    }

}