package nl.fontys.s3.bvbforum.business.impl.user;

import nl.fontys.s3.bvbforum.business.impl.user.GetUserUseCaseImpl;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserUseCaseImpl getUserUseCase;

    @Mock
    private AccessToken accessToken;

    @Test
    void Get_UserById_ReturnsUser() {
        // given
        UserEntity expectedUser = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(expectedUser));
        when(accessToken.getUserId()).thenReturn(expectedUser.getId());

        // when
        UserEntity actualUser = getUserUseCase.getUserById(1L);

        // then
        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);

        // verify
        verify(userRepository, times(1)).findById(1L);
        verify(accessToken, times(1)).getUserId();
    }
}