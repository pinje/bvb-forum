package nl.fontys.s3.bvbforum.business.impl.rating;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.UnauthorizedDataAccessException;
import nl.fontys.s3.bvbforum.business.exception.rating.RatingDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.rating.DeleteRatingUseCase;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.persistence.RatingRepository;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;
import nl.fontys.s3.bvbforum.persistence.entity.RoleEnum;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteRatingUseCaseImpl implements DeleteRatingUseCase {
    private final RatingRepository ratingRepository;
    private AccessToken accessToken;

    @Override
    public void deleteRating(long ratingId) {
        Optional<RatingEntity> ratingOptional = ratingRepository.findById(ratingId);

        if (ratingOptional.isEmpty()) {
            throw new RatingDoesntExistException();
        }

        RatingEntity rating = ratingOptional.get();

        if (!accessToken.hasRole(RoleEnum.ADMIN.name())) {
            if (accessToken.getUserId() != rating.getUser().getId()) {
                throw new UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
            }
        }

        this.ratingRepository.deleteById(ratingId);
    }
}
