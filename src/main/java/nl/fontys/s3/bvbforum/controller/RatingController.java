package nl.fontys.s3.bvbforum.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.rating.CreateRatingUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.rating.DeleteRatingUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.rating.GetRatingUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.rating.GetRatingsUseCase;
import nl.fontys.s3.bvbforum.configuration.security.isauthenticated.IsAuthenticated;
import nl.fontys.s3.bvbforum.domain.PlayerAverageRatingDTO;
import nl.fontys.s3.bvbforum.domain.request.rating.CreateRatingRequest;
import nl.fontys.s3.bvbforum.domain.request.rating.GetRatingsRequest;
import nl.fontys.s3.bvbforum.domain.response.rating.CreateRatingResponse;
import nl.fontys.s3.bvbforum.domain.response.rating.GetRatingsResponse;
import nl.fontys.s3.bvbforum.persistence.entity.RatingEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

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

    @GetMapping("/player")
    public ResponseEntity<GetRatingsResponse> getRatingsByPlayerId(@RequestParam(value = "id", required = true) Long playerId) {
        GetRatingsRequest request = new GetRatingsRequest();
        request.setId(playerId);
        return ResponseEntity.ok(getRatingsUseCase.getRatingsByPlayerId(request));
    }

    @GetMapping("/user")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public ResponseEntity<GetRatingsResponse> getRatingsByUserId(@RequestParam(value = "id", required = true) Long userId) {
        GetRatingsRequest request = new GetRatingsRequest();
        request.setId(userId);
        return ResponseEntity.ok(getRatingsUseCase.getRatingsByUserId(request));
    }

    @GetMapping("{id}")
    public RatingEntity getRatingById(@PathVariable(value = "id") final long id) { return getRatingUseCase.getRatingById(id); }

    @GetMapping("/player/{id}")
    public PlayerAverageRatingDTO getAverageRatingByPlayerId(@PathVariable(value = "playerId") final long playerId) { return getRatingUseCase.getAverageRatingByPlayerId(playerId); }

    @DeleteMapping("{ratingId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public ResponseEntity<Void> deleteRating(@PathVariable int ratingId) {
        deleteRatingUseCase.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }

}
