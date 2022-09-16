package nl.fontys.s3.bvbforum.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.GetAllUsersUseCase;
import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.domain.response.GetAllUsersResponse;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllUsersUseCaseImpl implements GetAllUsersUseCase {
    private final UserRepository userRepository;

    @Override
    public GetAllUsersResponse getAllUsers() {
        List<User> users = userRepository.findAll()
                .stream()
                .map(UserConverter::convert)
                .toList();

        return GetAllUsersResponse.builder()
                .users(users)
                .build();
    }
}
