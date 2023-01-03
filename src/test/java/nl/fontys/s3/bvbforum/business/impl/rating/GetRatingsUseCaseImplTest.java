package nl.fontys.s3.bvbforum.business.impl.rating;

import nl.fontys.s3.bvbforum.business.exception.rating.RatingDoesntExistException;
import nl.fontys.s3.bvbforum.domain.RatingInformationDTO;
import nl.fontys.s3.bvbforum.domain.response.rating.GetRatingsResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetRatingsUseCaseImplTest {
    @Mock
    private RatingRepository ratingRepository;
    @InjectMocks
    private GetRatingsUseCaseImpl getRatingsUseCase;

    @Test
    void GetAll_ExistingRatings_ReturnsAllRatings() {
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

        // set up mock objects
        when(ratingRepository.findAll()).thenReturn(List.of(ratingEntityOne, ratingEntityTwo));

        // when
        GetRatingsResponse actualResult = getRatingsUseCase.getRatings();

        // then
        RatingInformationDTO one = RatingInformationDTO.builder().id(1L).player(playerEntity).rating(8L).userId(1L).build();
        RatingInformationDTO two = RatingInformationDTO.builder().id(2L).player(playerEntity).rating(10L).userId(1L).build();
        GetRatingsResponse expectedResult = GetRatingsResponse.builder()
                .ratings(List.of(one, two))
                .build();

        assertEquals(expectedResult, actualResult);

        // verify
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    void GetAll_ExistingRatings_NoRatingsExist_ThrowsException() throws RatingDoesntExistException {
        // given

        // set up mock objects
        when(ratingRepository.findAll()).thenReturn(List.of());

        // when
        ResponseStatusException exception = assertThrows(RatingDoesntExistException.class, () -> {
            getRatingsUseCase.getRatings();
        });

        // then
        assertEquals("RATING_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    void GetAll_ExistingRatings_ReturnsAllRatings_withPlayerId() {
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

        // set up mock objects
        when(ratingRepository.findAllByPlayerId(22L)).thenReturn(List.of(ratingEntityOne, ratingEntityTwo));

        // when
        GetRatingsResponse actualResult = getRatingsUseCase.getRatingsByPlayerId(22L);

        // then
        RatingInformationDTO one = RatingInformationDTO.builder().id(1L).player(playerEntity).rating(8L).userId(1L).build();
        RatingInformationDTO two = RatingInformationDTO.builder().id(2L).player(playerEntity).rating(10L).userId(1L).build();
        GetRatingsResponse expectedResult = GetRatingsResponse.builder()
                .ratings(List.of(one, two))
                .build();

        assertEquals(expectedResult, actualResult);

        // verify
        verify(ratingRepository, times(1)).findAllByPlayerId(22L);
    }

    @Test
    void GetAll_ExistingRatings_ReturnsAllRatings_withUserId() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(111L)
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

        // set up mock objects
        when(ratingRepository.findAllByUserId(111L)).thenReturn(List.of(ratingEntityOne, ratingEntityTwo));

        // when
        GetRatingsResponse actualResult = getRatingsUseCase.getRatingsByUserId(111L);

        // then
        RatingInformationDTO one = RatingInformationDTO.builder().id(1L).player(playerEntity).rating(8L).userId(111L).build();
        RatingInformationDTO two = RatingInformationDTO.builder().id(2L).player(playerEntity).rating(10L).userId(111L).build();
        GetRatingsResponse expectedResult = GetRatingsResponse.builder()
                .ratings(List.of(one, two))
                .build();

        assertEquals(expectedResult, actualResult);

        // verify
        verify(ratingRepository, times(1)).findAllByUserId(111L);
    }
}