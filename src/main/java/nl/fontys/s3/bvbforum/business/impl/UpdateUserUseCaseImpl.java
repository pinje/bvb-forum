package nl.fontys.s3.bvbforum.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.UpdateUserUseCase;
import nl.fontys.s3.bvbforum.business.exception.InvalidUserException;
import nl.fontys.s3.bvbforum.domain.request.UpdateUserRequest;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;

    @Override
    public void updateUser(UpdateUserRequest request) {
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepository.findById(request.getId()));
        if (userOptional.isEmpty()) {
            throw new InvalidUserException("USER_ID_INVALID");
        }

        UserEntity user = userOptional.get();
        updateFields(request, user);
    }

    private void updateFields(UpdateUserRequest request, UserEntity user) {
        user.setUsername(request.getUsername());
        userRepository.update(user);
    }
}
