package nl.fontys.s3.bvbforum.business.impl.player;

import nl.fontys.s3.bvbforum.business.exception.player.PlayerDoesntExistException;
import nl.fontys.s3.bvbforum.business.exception.rating_post.RatingPostDoesntExistException;
import nl.fontys.s3.bvbforum.domain.Player;
import nl.fontys.s3.bvbforum.domain.response.player.GetPlayersResponse;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingPostPlayerRepository;
import nl.fontys.s3.bvbforum.persistence.entity.*;
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
    @Mock
    private RatingPostPlayerRepository ratingPostPlayerRepository;
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
    void GetAll_ExistingPlayers_ReturnsAllPlayers_ByRatingPostId() {
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

        RatingPostEntity ratingPostEntity = RatingPostEntity.builder()
                .id(111L)
                .start_year(2022)
                .end_year(2023)
                .matchday(1)
                .opponent("Schalke04")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        RatingPostPlayerEntity ratingPostPlayerEntityOne = RatingPostPlayerEntity.builder()
                        .id(1L)
                        .player(playerEntityOne)
                        .ratingPost(ratingPostEntity)
                        .build();

        RatingPostPlayerEntity ratingPostPlayerEntityTwo = RatingPostPlayerEntity.builder()
                .id(2L)
                .player(playerEntityTwo)
                .ratingPost(ratingPostEntity)
                .build();

        // set up mock objects
        when(ratingPostPlayerRepository.findRatingPostPlayerEntitiesByRatingPostId(111L))
                .thenReturn(List.of(ratingPostPlayerEntityOne, ratingPostPlayerEntityTwo));

        // call the method
        GetPlayersResponse actualResult = getPlayersUseCase.getPlayersByRatingPostId(111L);

        // then
        Player one = Player.builder().id(1L).firstname("marco").lastname("reus").position(PositionEnum.MF).build();
        Player two = Player.builder().id(2L).firstname("jude").lastname("bellingham").position(PositionEnum.MF).build();
        GetPlayersResponse expectedResult = GetPlayersResponse.builder().players(List.of(one, two)).build();

        assertEquals(expectedResult, actualResult);

        // verify
        verify(ratingPostPlayerRepository).findRatingPostPlayerEntitiesByRatingPostId(111L);
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

    @Test
    void GetAll_ExistingPlayers_ByRatingPostId_NoRatingPostExist_ThrowsException() throws RatingPostDoesntExistException {
        // given

        // set up mock objects
        when(ratingPostPlayerRepository.findRatingPostPlayerEntitiesByRatingPostId(111L)).thenReturn(List.of());

        // when
        ResponseStatusException exception = assertThrows(RatingPostDoesntExistException.class, () -> {
            getPlayersUseCase.getPlayersByRatingPostId(111L);
        });

        // then
        assertEquals("RATING_POST_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(ratingPostPlayerRepository, times(1)).findRatingPostPlayerEntitiesByRatingPostId(111L);
    }
}