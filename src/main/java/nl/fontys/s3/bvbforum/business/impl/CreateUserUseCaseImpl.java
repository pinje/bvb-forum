package nl.fontys.s3.bvbforum.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.CreateUserUseCase;
import nl.fontys.s3.bvbforum.business.exception.UserIdAlreadyExistsException;
import nl.fontys.s3.bvbforum.business.exception.UserUsernameAlreadyExistsException;
import nl.fontys.s3.bvbforum.domain.request.CreateUserRequest;
import nl.fontys.s3.bvbforum.domain.response.CreateUserResponse;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserUsernameAlreadyExistsException();
        }

        UserEntity saveUser = saveNewUser(request);

        return CreateUserResponse.builder()
                .userId(saveUser.getId())
                .build();
    }

    private UserEntity saveNewUser(CreateUserRequest request) {
        UserEntity newUser = UserEntity.builder()
                .username(request.getUsername())
                .build();

        return userRepository.save(newUser);
    }
}
