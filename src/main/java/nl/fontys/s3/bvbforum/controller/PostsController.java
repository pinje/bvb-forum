package nl.fontys.s3.bvbforum.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.post.CreatePostUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.post.GetAllPostsUseCase;
import nl.fontys.s3.bvbforum.domain.request.post.CreatePostRequest;
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

    @PostMapping()
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody @Valid CreatePostRequest request) {
        CreatePostResponse response = createPostUseCase.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetAllPostsResponse> getAllPosts() {
        return ResponseEntity.ok(getAllPostsUseCase.getAllPosts());
    }
}
