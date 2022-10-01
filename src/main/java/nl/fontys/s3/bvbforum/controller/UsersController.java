package nl.fontys.s3.bvbforum.controller;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.*;
import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.domain.request.CreateUserRequest;
import nl.fontys.s3.bvbforum.domain.request.UpdateUserRequest;
import nl.fontys.s3.bvbforum.domain.response.CreateUserResponse;
import nl.fontys.s3.bvbforum.domain.response.GetAllUsersResponse;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
public class UsersController {
    private final CreateUserUseCase createUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @PostMapping()
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GetAllUsersResponse> getAllUsers() {
        return ResponseEntity.ok(getAllUsersUseCase.getAllUsers());
    }

    @GetMapping("{id}")
    public UserEntity getUser(@PathVariable(value = "id") final long id) {
        final UserEntity userOptional = getUserUseCase.getUser(id);
        return userOptional;
    }

    @GetMapping("/user/{username}")
    public UserEntity getUser(@PathVariable(value = "username") final String username) {
        final UserEntity userOptional = getUserUseCase.getUser(username);
        return userOptional;
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") long id,
                                              @RequestBody @Valid UpdateUserRequest request) {
        request.setId(id);
        updateUserUseCase.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        deleteUserUseCase.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
