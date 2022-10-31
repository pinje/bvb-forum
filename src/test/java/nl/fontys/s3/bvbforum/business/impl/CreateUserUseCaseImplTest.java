package nl.fontys.s3.bvbforum.business.impl;

import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.domain.request.CreateUserRequest;
import nl.fontys.s3.bvbforum.domain.response.CreateUserResponse;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    void saveUser() {
        // given
        UserEntity userOne = UserEntity.builder()
                .id(1L)
                .username("one")
                .build();

        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userOne);

        // when
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username("one")
                .build();

        CreateUserResponse actualResult = createUserUseCase.createUser(createUserRequest);

        // then
        User user = User.builder().id(1L).username("one").build();
        CreateUserResponse expectedResult = CreateUserResponse.builder()
                .userId(user.getId())
                .build();

        assertEquals(expectedResult, actualResult);
        verify(userRepositoryMock).save(any(UserEntity.class));
    }

}