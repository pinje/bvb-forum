package nl.fontys.s3.bvbforum.business.impl.user;

import nl.fontys.s3.bvbforum.business.impl.user.GetUserUseCaseImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserUseCaseImpl getUserUseCase;

    @Test
    void Get_UserById_ReturnsUser() {
        // given
        UserEntity expectedUser = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(expectedUser));

        // when
        UserEntity actualUser = getUserUseCase.getUserById(1L);

        // then
        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).findById(anyLong());
    }
}