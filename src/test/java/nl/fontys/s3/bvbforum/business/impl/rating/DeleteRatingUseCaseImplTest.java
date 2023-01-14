package nl.fontys.s3.bvbforum.business.impl.rating;

import nl.fontys.s3.bvbforum.business.exception.UnauthorizedDataAccessException;
import nl.fontys.s3.bvbforum.business.exception.rating.RatingDoesntExistException;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.persistence.RatingRepository;
import nl.fontys.s3.bvbforum.persistence.entity.*;
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
class DeleteRatingUseCaseImplTest {
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private AccessToken accessToken;
    @InjectMocks
    private DeleteRatingUseCaseImpl deleteRatingUseCase;

    @Test
    void Delete_ExistingRating_AdminRole() {
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
                .id(555L)
                .start_year(2022)
                .end_year(2023)
                .matchday(1)
                .opponent("Schalke04")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        RatingEntity ratingEntity = RatingEntity.builder()
                .id(1L)
                .player(playerEntity)
                .rating(8L)
                .user(userEntity)
                .ratingPost(ratingPostEntity)
                .build();

        // set up mock objects
        when(ratingRepository.findById(ratingEntity.getId())).thenReturn(Optional.of(ratingEntity));
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(true);

        // when
        deleteRatingUseCase.deleteRating(ratingEntity.getId());

        // verify
        verify(ratingRepository, times(1)).deleteById(1L);
        verify(ratingRepository, times(1)).findById(ratingEntity.getId());
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
    }

    @Test
    void Delete_ExistingRating_NonAdminRoleSameUser() {
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
                .id(555L)
                .start_year(2022)
                .end_year(2023)
                .matchday(1)
                .opponent("Schalke04")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        RatingEntity ratingEntity = RatingEntity.builder()
                .id(1L)
                .player(playerEntity)
                .rating(8L)
                .user(userEntity)
                .ratingPost(ratingPostEntity)
                .build();

        // set up mock objects
        when(ratingRepository.findById(ratingEntity.getId())).thenReturn(Optional.of(ratingEntity));
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(ratingEntity.getUser().getId());

        // when
        deleteRatingUseCase.deleteRating(ratingEntity.getId());

        // verify
        verify(ratingRepository, times(1)).findById(ratingEntity.getId());
        verify(ratingRepository, times(1)).deleteById(ratingEntity.getId());
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
    }

    @Test
    void Delete_ExistingRating_NonAdminRoleDifferentUser_ThrowsException() throws UnauthorizedDataAccessException {
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
                .id(555L)
                .start_year(2022)
                .end_year(2023)
                .matchday(1)
                .opponent("Schalke04")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        RatingEntity ratingEntity = RatingEntity.builder()
                .id(1L)
                .player(playerEntity)
                .rating(8L)
                .user(userEntity)
                .ratingPost(ratingPostEntity)
                .build();

        // set up mock objects
        when(ratingRepository.findById(ratingEntity.getId())).thenReturn(Optional.of(ratingEntity));
        when(accessToken.hasRole(RoleEnum.ADMIN.name())).thenReturn(false);
        when(accessToken.getUserId()).thenReturn(444L);

        // when
        ResponseStatusException exception = assertThrows(UnauthorizedDataAccessException.class, () -> {
            deleteRatingUseCase.deleteRating(ratingEntity.getId());
        });

        // then
        assertEquals("USER_ID_NOT_FROM_LOGGED_IN_USER", exception.getReason());
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        // verify
        verify(ratingRepository, times(1)).findById(ratingEntity.getId());
        verify(accessToken, times(1)).hasRole(RoleEnum.ADMIN.name());
        verify(accessToken, times(1)).getUserId();
    }

    @Test
    void Delete_NonExistingRating_ThrowsException() throws RatingDoesntExistException {
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
                .id(555L)
                .start_year(2022)
                .end_year(2023)
                .matchday(1)
                .opponent("Schalke04")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        RatingEntity ratingEntity = RatingEntity.builder()
                .id(1L)
                .player(playerEntity)
                .rating(8L)
                .user(userEntity)
                .ratingPost(ratingPostEntity)
                .build();

        Long id = ratingEntity.getId();

        // set up mock objects
        doThrow(new RatingDoesntExistException()).when(ratingRepository).findById(1L);

        // when
        ResponseStatusException exception = assertThrows(RatingDoesntExistException.class, () -> {
            deleteRatingUseCase.deleteRating(id);
        });

        // then
        assertEquals("RATING_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(ratingRepository, times(1)).findById(1L);
    }

}