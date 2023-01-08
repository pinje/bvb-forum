package nl.fontys.s3.bvbforum.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.player.CreatePlayerUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.player.DeletePlayerUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.player.GetPlayerUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.player.GetPlayersUseCase;
import nl.fontys.s3.bvbforum.configuration.security.isauthenticated.IsAuthenticated;
import nl.fontys.s3.bvbforum.domain.request.player.CreatePlayerRequest;
import nl.fontys.s3.bvbforum.domain.response.player.CreatePlayerResponse;
import nl.fontys.s3.bvbforum.domain.response.player.GetPlayersResponse;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/players")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class PlayersController {
    private final CreatePlayerUseCase createPlayerUseCase;
    private final GetPlayersUseCase getPlayersUseCase;
    private final GetPlayerUseCase getPlayerUseCase;
    private final DeletePlayerUseCase deletePlayerUseCase;

    @PostMapping()
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<CreatePlayerResponse> createPlayer(@RequestBody @Valid CreatePlayerRequest request) {
        CreatePlayerResponse response = createPlayerUseCase.createPlayer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetPlayersResponse> getPlayers() {
        return ResponseEntity.ok(getPlayersUseCase.getPlayers());
    }

    @GetMapping("/ratingpost/{id}")
    public ResponseEntity<GetPlayersResponse> getPlayersByRatingPostId(@PathVariable(value = "id") final long id) {
        return ResponseEntity.ok(getPlayersUseCase.getPlayersByRatingPostId(id));
    }

    @GetMapping("{id}")
    public PlayerEntity getPlayerById(@PathVariable(value = "id") final long id) { return getPlayerUseCase.getPlayerById(id); }

    @DeleteMapping("{playerId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    public ResponseEntity<Void> deletePlayer(@PathVariable int playerId) {
        deletePlayerUseCase.deletePlayer(playerId);
        return ResponseEntity.noContent().build();
    }
}
