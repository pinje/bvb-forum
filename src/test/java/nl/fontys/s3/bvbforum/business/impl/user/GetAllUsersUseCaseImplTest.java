package nl.fontys.s3.bvbforum.business.impl.user;

import nl.fontys.s3.bvbforum.business.exception.user.UserDoesntExistException;
import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.domain.response.user.GetAllUsersResponse;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllUsersUseCaseImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private GetAllUsersUseCaseImpl getAllUsersUseCase;

    @Test
    void GetAll_ExistingUsers_ReturnsAllUsers() {
        // given
        UserEntity userEntityOne = UserEntity.builder()
                .id(1L)
                .username("one")
                .build();
        UserEntity userEntityTwo = UserEntity.builder()
                .id(2L)
                .username("two")
                .build();

        // set up mock objects
        when(userRepository.findAll()).thenReturn(List.of(userEntityOne, userEntityTwo));

        // when
        GetAllUsersResponse actualResult = getAllUsersUseCase.getAllUsers();

        // then
        User one = User.builder().id(1L).username("one").build();
        User two = User.builder().id(2L).username("two").build();
        GetAllUsersResponse expectedResult = GetAllUsersResponse.builder()
                .users(List.of(one, two))
                .build();

        assertEquals(expectedResult, actualResult);

        // verify
        verify(userRepository).findAll();
    }

    @Test
    void GetAll_ExistingUsers_NoUsersExist_ThrowsException() throws UserDoesntExistException {
        // given

        // set up mock objects
        when(userRepository.findAll()).thenReturn(List.of());

        // when
        ResponseStatusException exception = assertThrows(UserDoesntExistException.class, () -> getAllUsersUseCase.getAllUsers());

        // then
        assertEquals("USER_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(userRepository, times(1)).findAll();
    }
}