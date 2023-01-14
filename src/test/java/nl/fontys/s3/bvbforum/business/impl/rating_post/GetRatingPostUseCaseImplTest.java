package nl.fontys.s3.bvbforum.business.impl.rating_post;

import nl.fontys.s3.bvbforum.business.exception.rating_post.RatingPostDoesntExistException;
import nl.fontys.s3.bvbforum.persistence.RatingPostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.RatingPostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.TournamentEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
    void Get_RatingPostById_RatingPostNotFound() {
        // given
        long nonExistentRatingPostId = -1;

        // set up mock objects
        when(ratingPostRepository.findById(nonExistentRatingPostId)).thenReturn(Optional.empty());

        // when
        RatingPostEntity result = getRatingPostUseCase.getRatingPostById(nonExistentRatingPostId);

        // then
        assertNull(result);

        // verify
        verify(ratingPostRepository, times(1)).findById(nonExistentRatingPostId);
    }

    @Test
    void Get_MostRecentRatingPost_ReturnsMostRecentRatingPostByDate() {
        // given
        RatingPostEntity expectedRatingPost = RatingPostEntity.builder()
                .id(1L)
                .date(Timestamp.valueOf("2023-01-01 01:00:00"))
                .start_year(2022)
                .end_year(2023)
                .matchday(1)
                .opponent("Bayern Munich")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        RatingPostEntity ratingPostEntityTwo = RatingPostEntity.builder()
                .id(1L)
                .date(Timestamp.valueOf("2023-01-01 02:00:00"))
                .start_year(2022)
                .end_year(2023)
                .matchday(2)
                .opponent("Schalke04")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        // set up mock objects
        when(ratingPostRepository.findFirstByOrderByDateDesc()).thenReturn(expectedRatingPost);

        // when
        RatingPostEntity actualRatingPost = getRatingPostUseCase.getMostRecentRatingPost();

        // then
        assertNotNull(actualRatingPost);
        assertEquals(expectedRatingPost, actualRatingPost);

        // verify
        verify(ratingPostRepository, times(1)).findFirstByOrderByDateDesc();
    }

    @Test
    void Get_MostRecentRatingPost_NoExistingRatingPost_ThrowsException() throws RatingPostDoesntExistException {
        // given

        // set up mock objects
        when(ratingPostRepository.findFirstByOrderByDateDesc()).thenReturn(null);

        // when
        ResponseStatusException exception = assertThrows(RatingPostDoesntExistException.class, () -> {
            getRatingPostUseCase.getMostRecentRatingPost();
        });

        // then
        assertEquals("RATING_POST_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(ratingPostRepository, times(1)).findFirstByOrderByDateDesc();
    }
}