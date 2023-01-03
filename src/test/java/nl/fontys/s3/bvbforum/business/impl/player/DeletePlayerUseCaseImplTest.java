package nl.fontys.s3.bvbforum.business.impl.player;

import nl.fontys.s3.bvbforum.business.exception.player.PlayerDoesntExistException;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import nl.fontys.s3.bvbforum.persistence.entity.PositionEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePlayerUseCaseImplTest {
    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private DeletePlayerUseCaseImpl deletePlayerUseCase;

    @Test
    void Delete_ExistingPlayer() {
        // given
        PlayerEntity playerEntity = PlayerEntity.builder()
                .id(1L)
                .firstname("marco")
                .lastname("reus")
                .position(PositionEnum.MF)
                .build();

        // set up mock objects
        when(playerRepository.findById(playerEntity.getId())).thenReturn(Optional.of(playerEntity));


        // when
        deletePlayerUseCase.deletePlayer(playerEntity.getId());

        // verify
        verify(playerRepository, times(1)).deleteById(1L);
        verify(playerRepository, times(1)).findById(playerEntity.getId());
    }

    @Test
    void Delete_NonExistingPlayer_ThrowsException() throws PlayerDoesntExistException {
        // given
        PlayerEntity playerEntity = PlayerEntity.builder()
                .id(1L)
                .firstname("marco")
                .lastname("reus")
                .position(PositionEnum.MF)
                .build();

        Long id = playerEntity.getId();

        // set up mock objects
        doThrow(new PlayerDoesntExistException()).when(playerRepository).findById(1L);

        // when
        ResponseStatusException exception = assertThrows(PlayerDoesntExistException.class, () -> {
            deletePlayerUseCase.deletePlayer(id);
        });

        // then
        assertEquals("PLAYER_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(playerRepository, times(1)).findById(1L);

    }
}