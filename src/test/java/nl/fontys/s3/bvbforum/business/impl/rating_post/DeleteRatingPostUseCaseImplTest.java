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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRatingPostUseCaseImplTest {
    @Mock
    private RatingPostRepository ratingPostRepository;
    @InjectMocks
    private DeleteRatingPostUseCaseImpl deleteRatingPostUseCase;

    @Test
    void Delete_ExistingRatingPost() {
        // given
        RatingPostEntity ratingPostEntity = RatingPostEntity.builder()
                .id(1L)
                .start_year(2022)
                .end_year(2023)
                .matchday(1)
                .opponent("Bayern Munich")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        // set up mock objects
        when(ratingPostRepository.findById(ratingPostEntity.getId())).thenReturn(Optional.of(ratingPostEntity));

        // when
        deleteRatingPostUseCase.deleteRatingPost(ratingPostEntity.getId());

        // verify
        verify(ratingPostRepository, times(1)).deleteById(1L);
        verify(ratingPostRepository, times(1)).findById(1L);
    }

    @Test
    void Delete_NonExistingRatingPost_ThrowsException() throws RatingPostDoesntExistException {
        // given
        RatingPostEntity ratingPostEntity = RatingPostEntity.builder()
                .id(1L)
                .start_year(2022)
                .end_year(2023)
                .matchday(1)
                .opponent("Bayern Munich")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        Long id = ratingPostEntity.getId();

        // set up mock objects
        doThrow(new RatingPostDoesntExistException()).when(ratingPostRepository).findById(1L);

        // when
        ResponseStatusException exception = assertThrows(RatingPostDoesntExistException.class, () -> {
            deleteRatingPostUseCase.deleteRatingPost(id);
        });

        // then
        assertEquals("RATING_POST_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(ratingPostRepository, times(1)).findById(1L);
    }
}