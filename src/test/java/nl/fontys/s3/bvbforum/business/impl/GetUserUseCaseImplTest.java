package nl.fontys.s3.bvbforum.business.impl;

import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserUseCaseImpl getUserUseCase;

    @Test
    void getUserByUsername() {
        UserEntity expectedUser = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .build();

        when(userRepository.findByUsername(anyString())).thenReturn(expectedUser);

        UserEntity actualUser = getUserUseCase.getUserByUsername("hello");
        assertNotNull(actualUser);
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
    }

    @Test
    void getUserById() {
        UserEntity expectedUser = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(expectedUser));

        UserEntity actualUser = getUserUseCase.getUserById(1L);
        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
    }
}