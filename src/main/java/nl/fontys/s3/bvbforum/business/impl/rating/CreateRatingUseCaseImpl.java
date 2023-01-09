package nl.fontys.s3.bvbforum.business.impl.rating;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.rating.CreateRatingUseCase;
import nl.fontys.s3.bvbforum.domain.request.rating.CreateRatingRequest;
import nl.fontys.s3.bvbforum.domain.response.rating.CreateRatingResponse;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingPostRepository;
import nl.fontys.s3.bvbforum.persistence.RatingRepository;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CreateRatingUseCaseImpl implements CreateRatingUseCase {
    private RatingRepository ratingRepository;
    private PlayerRepository playerRepository;
    private UserRepository userRepository;
    private RatingPostRepository ratingPostRepository;

    @Transactional
    @Override
    public CreateRatingResponse createRating(CreateRatingRequest request) {
        RatingEntity saveRating = save(request);

        return CreateRatingResponse.builder()
                .ratingId(saveRating.getId())
                .build();
    }

    private RatingEntity save(CreateRatingRequest request) {
        RatingEntity newRating = RatingEntity.builder()
                .player(playerRepository.findById(request.getPlayerId())
                        .stream()
                        .findFirst().orElse(null))
                .rating(request.getRating())
                .user(userRepository.findById(request.getUserId())
                        .stream()
                        .findFirst().orElse(null))
                .ratingPost(ratingPostRepository.findById(request.getRatingPostId())
                        .stream()
                        .findFirst().orElse(null))
                .build();

        return ratingRepository.save(newRating);
    }
}
