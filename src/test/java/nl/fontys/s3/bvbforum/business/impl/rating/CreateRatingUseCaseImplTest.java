package nl.fontys.s3.bvbforum.business.impl.rating;

import nl.fontys.s3.bvbforum.domain.request.rating.CreateRatingRequest;
import nl.fontys.s3.bvbforum.domain.response.rating.CreateRatingResponse;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingRepository;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import nl.fontys.s3.bvbforum.persistence.entity.PositionEnum;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;
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
class CreateRatingUseCaseImplTest {
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PlayerRepository playerRepository;
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

        RatingEntity ratingEntity = RatingEntity.builder()
                .id(1L)
                .player(playerEntity)
                .rating(8L)
                .user(userEntity)
                .build();

        RatingEntity requestRating = RatingEntity.builder()
                .player(playerEntity)
                .rating(8L)
                .user(userEntity)
                .build();

        // set up mock objects
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(playerRepository.findById(22L)).thenReturn(Optional.of(playerEntity));
        when(ratingRepository.save(requestRating)).thenReturn(ratingEntity);

        // call the method
        CreateRatingRequest request = CreateRatingRequest.builder()
                .playerId(ratingEntity.getPlayer().getId())
                .rating(ratingEntity.getRating())
                .userId(ratingEntity.getUser().getId())
                .build();

        // when
        CreateRatingResponse response = createRatingUseCase.createRating(request);

        // then
        assertNotNull(response.getRatingId());

        // verify
        verify(ratingRepository, times(1)).save(requestRating);
        verify(userRepository, times(1)).findById(1L);
        verify(playerRepository, times(1)).findById(22L);
    }

}