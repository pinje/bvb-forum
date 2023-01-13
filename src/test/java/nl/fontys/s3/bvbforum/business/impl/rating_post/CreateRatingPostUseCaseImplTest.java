package nl.fontys.s3.bvbforum.business.impl.rating_post;

import nl.fontys.s3.bvbforum.domain.request.rating_post.CreateRatingPostRequest;
import nl.fontys.s3.bvbforum.domain.response.rating_post.CreateRatingPostResponse;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingPostPlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingPostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRatingPostUseCaseImplTest {
    @Mock
    private RatingPostRepository ratingPostRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private RatingPostPlayerRepository ratingPostPlayerRepository;
    @InjectMocks
    private CreateRatingPostUseCaseImpl createRatingPostUseCase;

    @Test
    void Add_ValidRatingPost_RatingPostSavedInRepository() {
        // given
        RatingPostEntity ratingPostEntity = RatingPostEntity.builder()
                .id(1L)
                .start_year(2022)
                .end_year(2023)
                .matchday(1)
                .opponent("Bayern Munich")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        RatingPostEntity requestRatingPostEntity = RatingPostEntity.builder()
                .start_year(2022)
                .end_year(2023)
                .matchday(1)
                .opponent("Bayern Munich")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        PlayerEntity playerOne = PlayerEntity.builder()
                .id(1L)
                .firstname("marco")
                .lastname("reus")
                .position(PositionEnum.MF)
                .build();

        PlayerEntity playerTwo = PlayerEntity.builder()
                .id(2L)
                .firstname("jude")
                .lastname("bellingham")
                .position(PositionEnum.MF)
                .build();

        RatingPostPlayerEntity requestRatingPostPlayerOne = RatingPostPlayerEntity.builder()
                .player(playerOne)
                .ratingPost(ratingPostEntity)
                .build();

        RatingPostPlayerEntity ratingPostPlayerEntityOne = RatingPostPlayerEntity.builder()
                .id(1L)
                .player(playerOne)
                .ratingPost(ratingPostEntity)
                .build();

        RatingPostPlayerEntity requestRatingPostPlayerTwo = RatingPostPlayerEntity.builder()
                .player(playerTwo)
                .ratingPost(ratingPostEntity)
                .build();

        RatingPostPlayerEntity ratingPostPlayerEntityTwo = RatingPostPlayerEntity.builder()
                .id(1L)
                .player(playerTwo)
                .ratingPost(ratingPostEntity)
                .build();

        // set up mock objects
        when(ratingPostPlayerRepository.save(requestRatingPostPlayerOne)).thenReturn(ratingPostPlayerEntityOne);
        when(ratingPostPlayerRepository.save(requestRatingPostPlayerTwo)).thenReturn(ratingPostPlayerEntityTwo);
        when(ratingPostRepository.save(requestRatingPostEntity)).thenReturn(ratingPostEntity);
        when(playerRepository.findById(1L)).thenReturn(Optional.ofNullable(playerOne));
        when(playerRepository.findById(2L)).thenReturn(Optional.ofNullable(playerTwo));


        // cal the method
        List<String> players = new ArrayList<>();
        players.add("1");
        players.add("2");
        CreateRatingPostRequest request = CreateRatingPostRequest.builder()
                .start_year("2022")
                .end_year("2023")
                .matchday("1")
                .opponent("Bayern Munich")
                .tournament(TournamentEnum.BUNDESLIGA)
                .playersId(players)
                .build();

        // when
        CreateRatingPostResponse response = createRatingPostUseCase.createRatingPost(request);

        // then
        assertNotNull(response.getRatingPostId());

        // verify
        verify(ratingPostPlayerRepository, times(1)).save(requestRatingPostPlayerOne);
        verify(ratingPostPlayerRepository, times(1)).save(requestRatingPostPlayerTwo);
        verify(ratingPostRepository, times(1)).save(requestRatingPostEntity);
        verify(playerRepository, times(1)).findById(1L);
        verify(playerRepository, times(1)).findById(2L);
    }

}