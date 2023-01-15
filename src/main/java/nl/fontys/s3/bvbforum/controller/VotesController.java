package nl.fontys.s3.bvbforum.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.vote.CreateVoteUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.vote.GetVoteUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.vote.UpdateVoteUseCase;
import nl.fontys.s3.bvbforum.domain.VoteInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.post.CreatePostRequest;
import nl.fontys.s3.bvbforum.domain.request.post.UpdatePostRequest;
import nl.fontys.s3.bvbforum.domain.request.vote.CreateVoteRequest;
import nl.fontys.s3.bvbforum.domain.request.vote.GetVoteRequest;
import nl.fontys.s3.bvbforum.domain.request.vote.UpdateVoteRequest;
import nl.fontys.s3.bvbforum.domain.response.post.CreatePostResponse;
import nl.fontys.s3.bvbforum.domain.response.vote.CreateVoteResponse;
import nl.fontys.s3.bvbforum.persistence.entity.VoteEntity;
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
    private final GetVoteUseCase getVoteUseCase;

    @PostMapping
    public ResponseEntity<CreateVoteResponse> createVote(@RequestBody @Valid CreateVoteRequest request) {
        CreateVoteResponse response = createVoteUseCase.createVote(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<Void> updateVote(@RequestParam(value = "postId") long postId,
                                           @RequestParam(value = "userId") long userId) {
        UpdateVoteRequest request = new UpdateVoteRequest();
        request.setPost(postId);
        request.setUser(userId);
        updateVoteUseCase.updateVote(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public VoteInformationDTO getVote(@RequestParam(value = "postId") long postId,
                                      @RequestParam(value = "userId") long userId) {
        GetVoteRequest request = new GetVoteRequest();
        request.setPost(postId);
        request.setUser(userId);
        return getVoteUseCase.getVote(request);
    }

    @GetMapping("/alreadyvoted")
    public boolean checkUserAlreadyVoted(@RequestParam(value = "postId") long postId,
                                           @RequestParam(value = "userId") long userId) {
        GetVoteRequest request = new GetVoteRequest();
        request.setPost(postId);
        request.setUser(userId);
        return getVoteUseCase.checkUserAlreadyVoted(request);
    }

    @GetMapping("/alreadyupvoted")
    public boolean checkUserAlreadyUpvoted(@RequestParam(value = "postId") long postId,
                                           @RequestParam(value = "userId") long userId) {
        GetVoteRequest request = new GetVoteRequest();
        request.setPost(postId);
        request.setUser(userId);
        return getVoteUseCase.checkUserAlreadyUpvoted(request);
    }

    @GetMapping("/alreadydownvoted")
    public boolean checkUserAlreadyDownvoted(@RequestParam(value = "postId") long postId,
                                           @RequestParam(value = "userId") long userId) {
        GetVoteRequest request = new GetVoteRequest();
        request.setPost(postId);
        request.setUser(userId);
        return getVoteUseCase.checkUserAlreadyDownvoted(request);
    }
}
