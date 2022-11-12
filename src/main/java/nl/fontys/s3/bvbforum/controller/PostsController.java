package nl.fontys.s3.bvbforum.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.post.*;
import nl.fontys.s3.bvbforum.domain.PostInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.post.CreatePostRequest;
import nl.fontys.s3.bvbforum.domain.request.post.UpdatePostRequest;
import nl.fontys.s3.bvbforum.domain.response.post.CreatePostResponse;
import nl.fontys.s3.bvbforum.domain.response.post.GetAllPostsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody @Valid CreatePostRequest request) {
        CreatePostResponse response = createPostUseCase.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetAllPostsResponse> getAllPosts() {
        return ResponseEntity.ok(getAllPostsUseCase.getAllPosts());
    }

    @GetMapping("{id}")
    public PostInformationDTO getPostById(@PathVariable(value = "id") final long id) { return getPostUseCase.getPostById(id); }

    @PutMapping("{id}")
    public ResponseEntity<Void> updatePost(@PathVariable("id") long id,
                                           @RequestBody @Valid UpdatePostRequest request) {
        request.setId(id);
        updatePostUseCase.updatePost(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/upvote")
    public void upvotePost(@PathVariable("id") long id) {
        updatePostUseCase.upvote(id);
    }

    @PutMapping("{id}/downvote")
    public void downvotePost(@PathVariable("id") long id) {
        updatePostUseCase.downvote(id);
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable int postId) {
        deletePostUseCase.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
