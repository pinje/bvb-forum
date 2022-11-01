package nl.fontys.s3.bvbforum.business.impl;

import nl.fontys.s3.bvbforum.business.exception.UserDoesntExistException;
import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.domain.request.UpdateUserRequest;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Stubber;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

        // then
        verify(userRepository, times(1)).deleteById(anyLong());

    }

    @Test
    void Delete_NonExistingUser_ThrowsException() throws UserDoesntExistException {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(111L)
                .username("Shuhei")
                .build();

        doThrow(new UserDoesntExistException()).when(userRepository).deleteById(anyLong());

        // when
        ResponseStatusException exception = assertThrows(UserDoesntExistException.class, () -> {
            deleteUserUseCase.deleteUser(userEntity.getId());
        });

        // then
        assertEquals("USER_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(userRepository, times(1)).deleteById(anyLong());
    }
}