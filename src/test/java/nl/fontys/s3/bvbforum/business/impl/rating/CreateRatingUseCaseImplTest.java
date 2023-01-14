package nl.fontys.s3.bvbforum.business.impl.rating;

import nl.fontys.s3.bvbforum.domain.request.rating.CreateRatingRequest;
import nl.fontys.s3.bvbforum.domain.response.rating.CreateRatingResponse;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingPostRepository;
import nl.fontys.s3.bvbforum.persistence.RatingRepository;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRatingUseCaseImplTest {
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private RatingPostRepository ratingPostRepository;
    @InjectMocks
    private CreateRatingUseCaseImpl createRatingUseCase;

    @Test
    void Add_ValidRating_RatingSavedInRepository() {
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

        RatingEntity requestRating = RatingEntity.builder()
                .player(playerEntity)
                .rating(8L)
                .user(userEntity)
                .ratingPost(ratingPostEntity)
                .build();

        // set up mock objects
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(playerRepository.findById(22L)).thenReturn(Optional.of(playerEntity));
        when(ratingPostRepository.findById(555L)).thenReturn(Optional.ofNullable(ratingPostEntity));
        when(ratingRepository.save(requestRating)).thenReturn(ratingEntity);

        // call the method
        CreateRatingRequest request = CreateRatingRequest.builder()
                .playerId(ratingEntity.getPlayer().getId())
                .rating(ratingEntity.getRating())
                .userId(ratingEntity.getUser().getId())
                .ratingPostId(ratingEntity.getRatingPost().getId())
                .build();

        // when
        CreateRatingResponse response = createRatingUseCase.createRating(request);

        // then
        assertNotNull(response.getRatingId());

        // verify
        verify(ratingRepository, times(1)).save(requestRating);
        verify(userRepository, times(1)).findById(1L);
        verify(playerRepository, times(1)).findById(22L);
        verify(ratingPostRepository, times(1)).findById(555L);
    }

}