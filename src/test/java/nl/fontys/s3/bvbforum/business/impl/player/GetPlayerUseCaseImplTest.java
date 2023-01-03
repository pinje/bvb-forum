package nl.fontys.s3.bvbforum.business.impl.player;

import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import nl.fontys.s3.bvbforum.persistence.entity.PositionEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPlayerUseCaseImplTest {
    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private GetPlayerUseCaseImpl getPlayerUseCase;

    @Test
    void Get_PlayerById_ReturnsPlayer() {
        // given
        PlayerEntity expectedPlayer = PlayerEntity.builder()
                .id(1L)
                .firstname("marco")
                .lastname("reus")
                .position(PositionEnum.MF)
                .build();

        // set up mock objects
        when(playerRepository.findById(expectedPlayer.getId())).thenReturn(Optional.of(expectedPlayer));

        // when
        PlayerEntity actualPlayer = getPlayerUseCase.getPlayerById(1L);

        // then
        assertEquals(expectedPlayer, actualPlayer);

        // verify
        verify(playerRepository, times(1)).findById(1L);
    }
}