package nl.fontys.s3.bvbforum.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.vote.CreateVoteUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.vote.UpdateVoteUseCase;
import nl.fontys.s3.bvbforum.domain.request.post.CreatePostRequest;
import nl.fontys.s3.bvbforum.domain.request.post.UpdatePostRequest;
import nl.fontys.s3.bvbforum.domain.request.vote.CreateVoteRequest;
import nl.fontys.s3.bvbforum.domain.request.vote.UpdateVoteRequest;
import nl.fontys.s3.bvbforum.domain.response.post.CreatePostResponse;
import nl.fontys.s3.bvbforum.domain.response.vote.CreateVoteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/votes")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class VotesController {
    private final CreateVoteUseCase createVoteUseCase;

    private final UpdateVoteUseCase updateVoteUseCase;

    @PostMapping
    public ResponseEntity<CreateVoteResponse> createVote(@RequestBody @Valid CreateVoteRequest request) {
        CreateVoteResponse response = createVoteUseCase.createVote(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateVote(@PathVariable("id") long id,
                                           @RequestBody @Valid UpdateVoteRequest request) {
        request.setId(id);
        updateVoteUseCase.updateVote(request);
        return ResponseEntity.noContent().build();
    }
}