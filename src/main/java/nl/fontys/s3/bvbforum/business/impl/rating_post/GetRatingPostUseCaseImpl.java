package nl.fontys.s3.bvbforum.business.impl.rating_post;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.rating_post.RatingPostDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.rating_post.GetRatingPostUseCase;
import nl.fontys.s3.bvbforum.persistence.RatingPostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.RatingPostEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetRatingPostUseCaseImpl implements GetRatingPostUseCase {
    private RatingPostRepository ratingPostRepository;

    @Override
    public RatingPostEntity getRatingPostById(long ratingPostId) {
        return ratingPostRepository.findById(ratingPostId)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    @Query("SELECT r FROM RatingPostEntity r ORDER BY r.date DESC")
    public RatingPostEntity getMostRecentRatingPost() {
        List<RatingPostEntity> ratingPosts = ratingPostRepository.findAll();

        if (ratingPosts.isEmpty()) {
            throw new RatingPostDoesntExistException();
        }

        return ratingPosts.get(0);
    }
}
