package nl.fontys.s3.bvbforum.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.post.*;
import nl.fontys.s3.bvbforum.configuration.security.isauthenticated.IsAuthenticated;
import nl.fontys.s3.bvbforum.domain.PostInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.post.CreatePostRequest;
import nl.fontys.s3.bvbforum.domain.request.post.GetAllPostsRequest;
import nl.fontys.s3.bvbforum.domain.request.post.UpdatePostRequest;
import nl.fontys.s3.bvbforum.domain.response.post.CreatePostResponse;
import nl.fontys.s3.bvbforum.domain.response.post.GetAllPostsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class PostsController {
    private final CreatePostUseCase createPostUseCase;

    private final GetAllPostsUseCase getAllPostsUseCase;

    private final GetPostUseCase getPostUseCase;

    private final UpdatePostUseCase updatePostUseCase;

    private final DeletePostUseCase deletePostUseCase;

    @PostMapping()
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody @Valid CreatePostRequest request) {
        CreatePostResponse response = createPostUseCase.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetAllPostsResponse> getAllPosts(@RequestParam(value = "userId", required = false) Long userId) {
        GetAllPostsRequest request = new GetAllPostsRequest();
        request.setUserId(userId);
        return ResponseEntity.ok(getAllPostsUseCase.getAllPosts(request));
    }

    @GetMapping("{id}")
    public PostInformationDTO getPostById(@PathVariable(value = "id") final long id) { return getPostUseCase.getPostById(id); }

    @PutMapping("{id}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public ResponseEntity<Void> updatePost(@PathVariable("id") long id,
                                           @RequestBody @Valid UpdatePostRequest request) {
        request.setId(id);
        updatePostUseCase.updatePost(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/upvote")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public void upvotePost(@PathVariable("id") long id) {
        updatePostUseCase.upvote(id);
    }

    @PutMapping("{id}/downvote")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public void downvotePost(@PathVariable("id") long id) {
        updatePostUseCase.downvote(id);
    }

    @DeleteMapping("{postId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN","ROLE_MEMBER"})
    public ResponseEntity<Void> deletePost(@PathVariable int postId) {
        deletePostUseCase.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
