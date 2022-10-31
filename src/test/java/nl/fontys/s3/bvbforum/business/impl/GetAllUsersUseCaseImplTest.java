package nl.fontys.s3.bvbforum.business.impl;

import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.domain.response.GetAllUsersResponse;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllUsersUseCaseImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private GetAllUsersUseCaseImpl getAllUsersUseCase;

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        UserEntity userEntityOne = UserEntity.builder()
                .id(1L)
                .username("one")
                .build();
        UserEntity userEntityTwo = UserEntity.builder()
                .id(2L)
                .username("two")
                .build();

        when(userRepositoryMock.findAll()).thenReturn(List.of(userEntityOne, userEntityTwo));

        GetAllUsersResponse actualResult = getAllUsersUseCase.getAllUsers();

        User one = User.builder().id(1L).username("one").build();
        User two = User.builder().id(2L).username("two").build();
        GetAllUsersResponse expectedResult = GetAllUsersResponse.builder()
                .users(List.of(one, two))
                .build();

        assertEquals(expectedResult, actualResult);
        verify(userRepositoryMock).findAll();
    }
}