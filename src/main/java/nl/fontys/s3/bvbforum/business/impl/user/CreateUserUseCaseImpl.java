package nl.fontys.s3.bvbforum.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.CreateUserUseCase;
import nl.fontys.s3.bvbforum.business.exception.UserUsernameAlreadyExistsException;
import nl.fontys.s3.bvbforum.domain.request.CreateUserRequest;
import nl.fontys.s3.bvbforum.domain.response.CreateUserResponse;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private UserRepository userRepository;

    @Transactional
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserUsernameAlreadyExistsException();
        }

        UserEntity saveUser = save(request);

        return CreateUserResponse.builder()
                .userId(saveUser.getId())
                .build();
    }

    private UserEntity save(CreateUserRequest request) {
        UserEntity newUser = UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();

        return userRepository.save(newUser);
    }
}
