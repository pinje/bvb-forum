package nl.fontys.s3.bvbforum.business.impl.rating;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.rating.RatingDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.rating.GetRatingsUseCase;
import nl.fontys.s3.bvbforum.domain.RatingInformationDTO;
import nl.fontys.s3.bvbforum.domain.response.rating.GetRatingsResponse;
import nl.fontys.s3.bvbforum.persistence.RatingRepository;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetRatingsUseCaseImpl implements GetRatingsUseCase {
    private RatingRepository ratingRepository;

    @Override
    public GetRatingsResponse getRatings() {
        List<RatingInformationDTO> ratings = ratingRepository.findAll()
                .stream()
                .map(RatingConverter::convert)
                .toList();

        if (ratings.isEmpty()) {
            throw new RatingDoesntExistException();
        }

        return GetRatingsResponse.builder()
                .ratings(ratings)
                .build();
    }

    @Override
    public GetRatingsResponse getRatingsByPlayerId(long playerId) {
        List<RatingEntity> results;
        results = ratingRepository.findAllByPlayerId(playerId);

        final GetRatingsResponse response = new GetRatingsResponse();

        List<RatingInformationDTO> ratings = results
                .stream()
                .map(RatingConverter::convert)
                .toList();

        response.setRatings(ratings);

        return response;
    }

    @Override
    public GetRatingsResponse getRatingsByUserId(long userId) {
        List<RatingEntity> results;
        results = ratingRepository.findAllByUserId(userId);

        final GetRatingsResponse response = new GetRatingsResponse();

        List<RatingInformationDTO> ratings = results
                .stream()
                .map(RatingConverter::convert)
                .toList();

        response.setRatings(ratings);

        return response;
    }
}
