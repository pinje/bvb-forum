package nl.fontys.s3.bvbforum.business.impl.rating;

import nl.fontys.s3.bvbforum.domain.PlayerAverageRatingDTO;
import nl.fontys.s3.bvbforum.domain.RatingInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.rating.GetRatingRequest;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingRepository;
import nl.fontys.s3.bvbforum.persistence.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetRatingUseCaseImplTest {
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private GetRatingUseCaseImpl getRatingUseCase;

    @Test
    void Get_RatingById_ReturnsRating() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123456")
                .build();

        PlayerEntity playerEntity = PlayerEntity.builder()
                .id(22L)
                .firstname("marco")
                .lastname("reus")
                .position(PositionEnum.MF)
                .build();

        RatingEntity ratingEntity = RatingEntity.builder()
                .id(1L)
                .player(playerEntity)
                .rating(8L)
                .user(userEntity)
                .build();

        RatingInformationDTO expectedRating = RatingInformationDTO.builder()
                .id(1L)
                .player(playerEntity)
                .rating(8L)
                .userId(userEntity.getId())
                .build();

        // set up mock objects
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(ratingEntity));

        // when
        RatingInformationDTO actualRating = getRatingUseCase.getRatingById(1L);

        // then
        assertNotNull(actualRating);
        assertEquals(expectedRating, actualRating);

        // verify
        verify(ratingRepository, times(1)).findById(1L);
    }

    @Test
    void Get_RatingById_RatingNotFound() {
        // given
        long nonExistentRatingId = -1;

        // set up mock objects
        when(ratingRepository.findById(nonExistentRatingId)).thenReturn(Optional.empty());

        // when
        RatingInformationDTO result = getRatingUseCase.getRatingById(nonExistentRatingId);

        // then
        assertNull(result);

        // verify
        verify(ratingRepository, times(1)).findById(nonExistentRatingId);
    }

    @Test
    void Get_RatingByPlayerId_ReturnsAverageRatingOfPlayer() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123456")
                .build();

        PlayerEntity playerEntity = PlayerEntity.builder()
                .id(22L)
                .firstname("marco")
                .lastname("reus")
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

        RatingEntity ratingEntityOne = RatingEntity.builder()
                .id(1L)
                .player(playerEntity)
                .rating(8L)
                .user(userEntity)
                .ratingPost(ratingPostEntity)
                .build();

        RatingEntity ratingEntityTwo = RatingEntity.builder()
                .id(2L)
                .player(playerEntity)
                .rating(10L)
                .user(userEntity)
                .ratingPost(ratingPostEntity)
                .build();

        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        PlayerAverageRatingDTO expectedAverageRating = PlayerAverageRatingDTO.builder()
                .averageRating(decimalFormat.format(9D))
                .player(playerEntity)
                .build();

        // set up mock objects
        when(ratingRepository.findAllByPlayerId(22L)).thenReturn(List.of(ratingEntityOne, ratingEntityTwo));
        when(playerRepository.findById(22L)).thenReturn(Optional.ofNullable(playerEntity));

        // when
        PlayerAverageRatingDTO actualAverageRating = getRatingUseCase.getAverageRatingByPlayerId(22L);

        // then
        assertNotNull(actualAverageRating);
        assertEquals(expectedAverageRating, actualAverageRating);

        // verify
        verify(ratingRepository, times(1)).findAllByPlayerId(22L);
        verify(playerRepository, times(1)).findById(22L);
    }

    @Test
    void Get_RatingByPlayerId_PlayerNotFound_NoAverage() {
        // given
        long nonExistentPlayerId = -1;
        List<RatingEntity> emptyList = new ArrayList<>();

        // set up mock objects
        when(ratingRepository.findAllByPlayerId(nonExistentPlayerId)).thenReturn(emptyList);

        // when
        PlayerAverageRatingDTO result = getRatingUseCase.getAverageRatingByPlayerId(nonExistentPlayerId);

        // then
        assertEquals(String.format("%.2f", 0.00), result.getAverageRating());

        // verify
        verify(ratingRepository, times(1)).findAllByPlayerId(nonExistentPlayerId);
    }

    @Test
    void Get_RatingByPlayerId_RatingNotFound_NoAverage() {
        // given
        long playerId = 1;
        List<RatingEntity> emptyList = new ArrayList<>();

        // set up mock objects
        when(ratingRepository.findAllByPlayerId(playerId)).thenReturn(emptyList);

        // when
        PlayerAverageRatingDTO result = getRatingUseCase.getAverageRatingByPlayerId(playerId);

        // then
        assertEquals(String.format("%.2f", 0.00), result.getAverageRating());

        // verify
        verify(ratingRepository, times(1)).findAllByPlayerId(playerId);

    }

    @Test
    void Get_UserAlreadyVoted_ReturnsTrue() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123456")
                .build();

        PlayerEntity playerEntity = PlayerEntity.builder()
                .id(22L)
                .firstname("marco")
                .lastname("reus")
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

        RatingEntity ratingEntityOne = RatingEntity.builder()
                .id(1L)
                .player(playerEntity)
                .rating(8L)
                .user(userEntity)
                .ratingPost(ratingPostEntity)
                .build();

        RatingEntity ratingEntityTwo = RatingEntity.builder()
                .id(2L)
                .player(playerEntity)
                .rating(10L)
                .user(userEntity)
                .ratingPost(ratingPostEntity)
                .build();

        // set up mock objects
        when(ratingRepository.findAllByUserId(1L)).thenReturn(List.of(ratingEntityOne, ratingEntityTwo));

        // call the method
        GetRatingRequest request = GetRatingRequest.builder()
                .userId(1L)
                .ratingPostId(111L)
                .build();
        // when
        boolean result = getRatingUseCase.checkUserAlreadyVoted(request);

        // then
        assertTrue(result);

        // verify
        verify(ratingRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void Get_UserNeverVoted_ReturnsFalse() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123456")
                .build();

        PlayerEntity playerEntity = PlayerEntity.builder()
                .id(22L)
                .firstname("marco")
                .lastname("reus")
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

        List<RatingEntity> emptyList = new ArrayList<>();

        // set up mock objects
        when(ratingRepository.findAllByUserId(1L)).thenReturn(emptyList);

        // call the method
        GetRatingRequest request = GetRatingRequest.builder()
                .userId(1L)
                .ratingPostId(111L)
                .build();
        // when
        boolean result = getRatingUseCase.checkUserAlreadyVoted(request);

        // then
        assertFalse(result);

        // verify
        verify(ratingRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void Get_UserAlreadyVoted_UserNotFound_ReturnsFalse() {
        // given
        long nonExistentUserId = -1;

        List<RatingEntity> emptyList = new ArrayList<>();

        // set up mock objects
        when(ratingRepository.findAllByUserId(nonExistentUserId)).thenReturn(emptyList);

        // call the method
        GetRatingRequest request = GetRatingRequest.builder()
                .userId(nonExistentUserId)
                .ratingPostId(111L)
                .build();
        // when
        boolean result = getRatingUseCase.checkUserAlreadyVoted(request);

        // then
        assertFalse(result);

        // verify
        verify(ratingRepository, times(1)).findAllByUserId(nonExistentUserId);
    }
}