package nl.fontys.s3.bvbforum.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.user.CreateUserUseCase;
import nl.fontys.s3.bvbforum.business.exception.user.UserUsernameAlreadyExistsException;
import nl.fontys.s3.bvbforum.domain.request.user.CreateUserRequest;
import nl.fontys.s3.bvbforum.domain.response.user.CreateUserResponse;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.RoleEnum;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import nl.fontys.s3.bvbforum.persistence.entity.UserRoleEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity newUser = UserEntity.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(RoleEnum.MEMBER)
                        .build()
        ));

        return userRepository.save(newUser);
    }
}
