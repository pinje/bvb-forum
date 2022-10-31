package nl.fontys.s3.bvbforum.business.impl;

import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void deleteUser() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .build();
        UserEntity userTwo = UserEntity.builder()
                .id(2L)
                .username("Fontys")
                .build();
        when(userRepository.findAll()).thenReturn(List.of(userEntity, userTwo));

        // when
        userRepository.deleteById(2L);

        // given
        User user = User.builder()
                .id(1L)
                .username("Shuhei")
                .build();
        assertEquals(user, userRepository.findAll());
        verify(userRepository).deleteById(2L);
    }
}