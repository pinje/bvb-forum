package nl.fontys.s3.bvbforum.business.impl.player;

import nl.fontys.s3.bvbforum.business.exception.player.PlayerDoesntExistException;
import nl.fontys.s3.bvbforum.domain.Player;
import nl.fontys.s3.bvbforum.domain.response.player.GetPlayersResponse;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPlayersUseCaseImplTest {
    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks GetPlayersUseCaseImpl getPlayersUseCase;

    @Test
    void GetAll_ExistingPlayers_ReturnsAllPlayers() {
        // given
        PlayerEntity playerEntityOne = PlayerEntity.builder()
                .id(1L)
                .firstname("marco")
                .lastname("reus")
                .position(PositionEnum.MF)
                .build();

        PlayerEntity playerEntityTwo = PlayerEntity.builder()
                .id(2L)
                .firstname("jude")
                .lastname("bellingham")
                .position(PositionEnum.MF)
                .build();

        // set up mock objects
        when(playerRepository.findAll()).thenReturn(List.of(playerEntityOne, playerEntityTwo));

        // when
        GetPlayersResponse actualResult = getPlayersUseCase.getPlayers();

        // then
        Player one = Player.builder().id(1L).firstname("marco").lastname("reus").position(PositionEnum.MF).build();
        Player two = Player.builder().id(2L).firstname("jude").lastname("bellingham").position(PositionEnum.MF).build();
        GetPlayersResponse expectedResult = GetPlayersResponse.builder()
                .players(List.of(one, two))
                .build();

        assertEquals(expectedResult, actualResult);

        // verify
        verify(playerRepository).findAll();
    }

    @Test
    void GetAll_ExistingPlayers_NoPlayersExist_ThrowsException() throws PlayerDoesntExistException {
        // given

        // set up mock objects
        when(playerRepository.findAll()).thenReturn(List.of());

        // when
        ResponseStatusException exception = assertThrows(PlayerDoesntExistException.class, () -> {
            getPlayersUseCase.getPlayers();
        });

        // then
        assertEquals("PLAYER_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(playerRepository, times(1)).findAll();
    }
}