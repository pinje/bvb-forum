package nl.fontys.s3.bvbforum.business.impl.rating;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.rating.RatingDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.rating.GetRatingsUseCase;
import nl.fontys.s3.bvbforum.domain.PlayerAverageRatingDTO;
import nl.fontys.s3.bvbforum.domain.RatingInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.rating.GetRatingsByPositionRequest;
import nl.fontys.s3.bvbforum.domain.response.rating.GetRatingsResponse;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import nl.fontys.s3.bvbforum.persistence.entity.PositionEnum;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GetRatingsUseCaseImpl implements GetRatingsUseCase {
    private RatingRepository ratingRepository;
    private PlayerRepository playerRepository;

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

    @Override
    public List<PlayerAverageRatingDTO> getAverageRatings() {
        // gather all players
        List<PlayerEntity> players;
        players = playerRepository.findAll();

        return generate(players);
    }

    @Override
    public List<PlayerAverageRatingDTO> getAverageRatingsByPosition(GetRatingsByPositionRequest request) {
        // gather all players
        List<PlayerEntity> players;
        players = playerRepository.findAllByPosition(request.getPosition());

        return generate(players);
    }

    private List<PlayerAverageRatingDTO> generate(List<PlayerEntity> players) {
        List<PlayerAverageRatingDTO> results = new ArrayList<>();

        // for each player, get their average rating and store
        for (PlayerEntity player : players) {
            List<RatingEntity> list;
            list = ratingRepository.findAll();

            double averageRating = list.stream()
                    .mapToInt(ratingEntity -> Math.toIntExact(ratingEntity.getRating()))
                    .average()
                    .orElse(0.0);

            String formattedAverageRating = String.format("%.2f", averageRating);

            PlayerAverageRatingDTO playerAverageRatingDTO = PlayerAverageRatingDTO.builder()
                    .player(player)
                    .averageRating(formattedAverageRating)
                    .build();

            results.add(playerAverageRatingDTO);
        }

        return results;
    }
}
