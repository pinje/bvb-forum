package nl.fontys.s3.bvbforum.business.impl.rating_post;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.rating_post.RatingPostDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.rating_post.DeleteRatingPostUseCase;
import nl.fontys.s3.bvbforum.persistence.RatingPostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.RatingPostEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteRatingPostUseCaseImpl implements DeleteRatingPostUseCase {
    private final RatingPostRepository ratingPostRepository;

    @Override
    public void deleteRatingPost(long ratingPostId) {
        Optional<RatingPostEntity> ratingPostOptional = ratingPostRepository.findById(ratingPostId);

        if (ratingPostOptional.isEmpty()) {
            throw new RatingPostDoesntExistException();
        }

        this.ratingPostRepository.deleteById(ratingPostId);
    }
}
