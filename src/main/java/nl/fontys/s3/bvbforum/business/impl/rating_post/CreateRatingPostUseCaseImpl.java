package nl.fontys.s3.bvbforum.business.impl.rating_post;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.rating_post.CreateRatingPostUseCase;
import nl.fontys.s3.bvbforum.domain.request.rating_post.CreateRatingPostRequest;
import nl.fontys.s3.bvbforum.domain.response.rating_post.CreateRatingPostResponse;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingPostPlayerRepository;
import nl.fontys.s3.bvbforum.persistence.RatingPostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.RatingPostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.RatingPostPlayerEntity;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class CreateRatingPostUseCaseImpl implements CreateRatingPostUseCase {
    private RatingPostRepository ratingPostRepository;
    private RatingPostPlayerRepository ratingPostPlayerRepository;
    private PlayerRepository playerRepository;

    @Transactional
    @Override
    public CreateRatingPostResponse createRatingPost(CreateRatingPostRequest request) {
        RatingPostEntity saveRatingPost = save(request);

        List<String> players = request.getPlayersId();

        for (String playerId : players) {
            RatingPostPlayerEntity ratingPostPlayer = RatingPostPlayerEntity.builder()
                    .player(playerRepository.findById(Long.valueOf(playerId)).stream().findFirst().orElse(null))
                    .ratingPost(saveRatingPost)
                    .build();
            ratingPostPlayerRepository.save(ratingPostPlayer);
        }

        return CreateRatingPostResponse.builder()
                .ratingPostId(saveRatingPost.getId())
                .build();
    }

    private RatingPostEntity save(CreateRatingPostRequest request) {
        DateTime date = DateTime.now();
        Timestamp ts = new Timestamp(date.toDateTime().getMillis());

        RatingPostEntity newRatingPost = RatingPostEntity.builder()
                .date(ts)
                .start_year(Integer.valueOf(request.getStart_year()))
                .end_year(Integer.valueOf(request.getEnd_year()))
                .matchday(Integer.valueOf(request.getMatchday()))
                .opponent(request.getOpponent())
                .tournament(request.getTournament())
                .build();

        return ratingPostRepository.save(newRatingPost);
    }
}
