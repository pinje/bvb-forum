package nl.fontys.s3.bvbforum.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.rating_post.CreateRatingPostUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.rating_post.DeleteRatingPostUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.rating_post.GetRatingPostUseCase;
import nl.fontys.s3.bvbforum.configuration.security.isauthenticated.IsAuthenticated;
import nl.fontys.s3.bvbforum.domain.request.rating_post.CreateRatingPostRequest;
import nl.fontys.s3.bvbforum.domain.response.rating_post.CreateRatingPostResponse;
import nl.fontys.s3.bvbforum.persistence.entity.RatingPostEntity;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/ratingposts")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class RatingPostController {
    private final CreateRatingPostUseCase createRatingPostUseCase;
    private final GetRatingPostUseCase getRatingPostUseCase;
    private final DeleteRatingPostUseCase deleteRatingPostUseCase;

    @PostMapping()
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<CreateRatingPostResponse> createRatingPost(@RequestBody @Valid CreateRatingPostRequest request) {
        CreateRatingPostResponse response = createRatingPostUseCase.createRatingPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{id}")
    public RatingPostEntity getRatingPostById(@PathVariable(value = "id") final long id) {
        return getRatingPostUseCase.getRatingPostById(id);
    }

    @GetMapping("/latest")
    public RatingPostEntity getMostRecentRatingPost() {
        return getRatingPostUseCase.getMostRecentRatingPost();
    }

    @DeleteMapping("{ratingPostId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteRatingPost(@PathVariable int ratingPostId) {
        deleteRatingPostUseCase.deleteRatingPost(ratingPostId);
        return ResponseEntity.noContent().build();
    }
}
