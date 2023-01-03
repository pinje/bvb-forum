package nl.fontys.s3.bvbforum.business.impl.rating;

import nl.fontys.s3.bvbforum.domain.PlayerAverageRatingDTO;
import nl.fontys.s3.bvbforum.domain.RatingInformationDTO;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import nl.fontys.s3.bvbforum.persistence.entity.PositionEnum;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        RatingEntity ratingEntityOne = RatingEntity.builder()
                .id(1L)
                .player(playerEntity)
                .rating(8L)
                .user(userEntity)
                .build();

        RatingEntity ratingEntityTwo = RatingEntity.builder()
                .id(2L)
                .player(playerEntity)
                .rating(10L)
                .user(userEntity)
                .build();

        PlayerAverageRatingDTO expectedAverageRating = PlayerAverageRatingDTO.builder()
                .averageRating(9D)
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
}