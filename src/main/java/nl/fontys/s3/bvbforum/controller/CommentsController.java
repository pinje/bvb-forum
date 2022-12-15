package nl.fontys.s3.bvbforum.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.comment.*;
import nl.fontys.s3.bvbforum.configuration.security.isauthenticated.IsAuthenticated;
import nl.fontys.s3.bvbforum.domain.CommentInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.comment.CreateCommentRequest;
import nl.fontys.s3.bvbforum.domain.request.comment.GetCommentsRequest;
import nl.fontys.s3.bvbforum.domain.request.comment.UpdateCommentRequest;
import nl.fontys.s3.bvbforum.domain.response.comment.CreateCommentResponse;
import nl.fontys.s3.bvbforum.domain.response.comment.GetCommentsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class CommentsController {
    private final CreateCommentUseCase createCommentUseCase;
    private final GetCommentsUseCase getCommentsUseCase;
    private final UpdateCommentUseCase updateCommentUseCase;
    private final GetCommentUseCase getCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;

    @PostMapping()
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public ResponseEntity<CreateCommentResponse> createComment(@RequestBody @Valid CreateCommentRequest request) {
        CreateCommentResponse response = createCommentUseCase.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/post")
    public ResponseEntity<GetCommentsResponse> getCommentsByPostId(@RequestParam(value = "postId", required = false) Long postId) {
        GetCommentsRequest request = new GetCommentsRequest();
        request.setId(postId);
        return ResponseEntity.ok(getCommentsUseCase.getCommentsByPostId(request));
    }

    @GetMapping("/user")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public ResponseEntity<GetCommentsResponse> getCommentsByUserId(@RequestParam(value = "userId", required = false) Long userId) {
        GetCommentsRequest request = new GetCommentsRequest();
        request.setId(userId);
        return ResponseEntity.ok(getCommentsUseCase.getCommentsByUserId(request));
    }

    @GetMapping("{id}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    public CommentInformationDTO getCommentById(@PathVariable(value = "id") final long id) { return getCommentUseCase.getCommentById(id); }

    @PutMapping("{id}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public ResponseEntity<Void> updateComment(@PathVariable("id") long id,
                                           @RequestBody @Valid UpdateCommentRequest request) {
        request.setId(id);
        updateCommentUseCase.updateComment(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{commentId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public ResponseEntity<Void> deleteComment(@PathVariable int commentId) {
        deleteCommentUseCase.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
