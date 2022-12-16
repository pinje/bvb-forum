package nl.fontys.s3.bvbforum.business.impl.user;

import nl.fontys.s3.bvbforum.business.exception.user.UserDoesntExistException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DeleteUserUseCaseImpl deleteUserUseCase;

    @Test
    void Delete_ExistingUser_ReturnsEmpty() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(111L)
                .username("Shuhei")
                .build();

        // when
        deleteUserUseCase.deleteUser(userEntity.getId());

        // verify
        verify(userRepository, times(1)).deleteById(111L);
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