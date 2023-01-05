package nl.fontys.s3.bvbforum.business.impl.rating_post;

import nl.fontys.s3.bvbforum.persistence.RatingPostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.RatingPostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.TournamentEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetRatingPostUseCaseImplTest {
    @Mock
    private RatingPostRepository ratingPostRepository;
    @InjectMocks
    private GetRatingPostUseCaseImpl getRatingPostUseCase;

    @Test
    void Get_RatingPostById_ReturnsRatingPost() {
        // given
        RatingPostEntity expectedRatingPost = RatingPostEntity.builder()
                .id(1L)
                .start_year(2022)
                .end_year(2023)
                .matchday(1)
                .opponent("Bayern Munich")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        // set up mock objects
        when(ratingPostRepository.findById(1L)).thenReturn(Optional.ofNullable(expectedRatingPost));

        // when
        RatingPostEntity actualRatingPost = getRatingPostUseCase.getRatingPostById(1L);

        // then
        assertNotNull(actualRatingPost);
        assertEquals(expectedRatingPost, actualRatingPost);

        // verify
        verify(ratingPostRepository, times(1)).findById(1L);
    }

    @Test
    void Get_MostRecentRatingPost_ReturnsMostRecentRatingPostByDate() {
        // given
        RatingPostEntity expectedRatingPost = RatingPostEntity.builder()
                .id(1L)
                .date(Timestamp.valueOf("2023-01-01 00:00:00"))
                .start_year(2022)
                .end_year(2023)
                .matchday(1)
                .opponent("Bayern Munich")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        RatingPostEntity ratingPostEntityTwo = RatingPostEntity.builder()
                .id(1L)
                .date(Timestamp.valueOf("2023-01-02 00:00:00"))
                .start_year(2022)
                .end_year(2023)
                .matchday(2)
                .opponent("Schalke04")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        // set up mock objects
        when(ratingPostRepository.findAll()).thenReturn(List.of(expectedRatingPost, ratingPostEntityTwo));

        // when
        RatingPostEntity actualRatingPost = getRatingPostUseCase.getMostRecentRatingPost();

        // then
        assertNotNull(actualRatingPost);
        assertEquals(expectedRatingPost, actualRatingPost);

        // verify
        verify(ratingPostRepository, times(1)).findAll();
    }
}