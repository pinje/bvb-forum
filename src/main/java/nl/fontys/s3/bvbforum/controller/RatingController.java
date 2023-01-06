package nl.fontys.s3.bvbforum.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.rating.CreateRatingUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.rating.DeleteRatingUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.rating.GetRatingUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.rating.GetRatingsUseCase;
import nl.fontys.s3.bvbforum.configuration.security.isauthenticated.IsAuthenticated;
import nl.fontys.s3.bvbforum.domain.PlayerAverageRatingDTO;
import nl.fontys.s3.bvbforum.domain.RatingInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.rating.CreateRatingRequest;
import nl.fontys.s3.bvbforum.domain.request.rating.GetRatingsByPositionRequest;
import nl.fontys.s3.bvbforum.domain.response.rating.CreateRatingResponse;
import nl.fontys.s3.bvbforum.domain.response.rating.GetAverageRatingsResponse;
import nl.fontys.s3.bvbforum.domain.response.rating.GetRatingsResponse;
import nl.fontys.s3.bvbforum.persistence.entity.PositionEnum;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ratings")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class RatingController {
    private final CreateRatingUseCase createRatingUseCase;
    private final GetRatingsUseCase getRatingsUseCase;
    private final GetRatingUseCase getRatingUseCase;
    private final DeleteRatingUseCase deleteRatingUseCase;

    @PostMapping()
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public ResponseEntity<CreateRatingResponse> createRating(@RequestBody @Valid CreateRatingRequest request) {
        CreateRatingResponse response = createRatingUseCase.createRating(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetRatingsResponse> getRatings() {
        return ResponseEntity.ok(getRatingsUseCase.getRatings());
    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<GetRatingsResponse> getRatingsByPlayerId(@PathVariable(value = "playerId") final long playerId) {
        return ResponseEntity.ok(getRatingsUseCase.getRatingsByPlayerId(playerId));
    }

    @GetMapping("/user/{userId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public ResponseEntity<GetRatingsResponse> getRatingsByUserId(@PathVariable(value = "userId") final long userId) {
        return ResponseEntity.ok(getRatingsUseCase.getRatingsByUserId(userId));
    }

    @GetMapping("/average")
    public ResponseEntity<GetAverageRatingsResponse> getAverageRatings() {
        return ResponseEntity.ok(getRatingsUseCase.getAverageRatings());
    }

    @GetMapping("/average_position")
    public ResponseEntity<GetAverageRatingsResponse> getAverageRatingsByPosition(@RequestBody @Valid GetRatingsByPositionRequest request) {
        return ResponseEntity.ok(getRatingsUseCase.getAverageRatingsByPosition(request));
    }

    @GetMapping("{id}")
    public RatingInformationDTO getRatingById(@PathVariable(value = "id") final long id) { return getRatingUseCase.getRatingById(id); }

    @GetMapping("/average/{playerId}")
    public PlayerAverageRatingDTO getAverageRatingByPlayerId(@PathVariable(value = "playerId") final long playerId) { return getRatingUseCase.getAverageRatingByPlayerId(playerId); }

    @DeleteMapping("{ratingId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public ResponseEntity<Void> deleteRating(@PathVariable int ratingId) {
        deleteRatingUseCase.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }

}
