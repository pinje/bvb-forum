package nl.fontys.s3.bvbforum.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.GetUserUseCase;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private UserRepository userRepository;

    @Override
    public UserEntity getUser(long userId) {
        UserEntity userEntity = userRepository.findById(userId);
        return userEntity;
    }
}
