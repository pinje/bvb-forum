package nl.fontys.s3.bvbforum.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.GetUserUseCase;
import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private UserRepository userRepository;

    @Override
    public UserEntity getUserById(long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .stream().filter(userEntity1 -> userEntity1.getId() == userId)
                .findFirst()
                .orElse(null);
        return userEntity;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        return userEntity;
    }
}
