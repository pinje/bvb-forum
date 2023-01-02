package nl.fontys.s3.bvbforum.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.user.UserDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.user.GetAllUsersUseCase;
import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.domain.response.user.GetAllUsersResponse;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class GetAllUsersUseCaseImpl implements GetAllUsersUseCase {
    private UserRepository userRepository;

    @Transactional
    @Override
    public GetAllUsersResponse getAllUsers() {
        List<User> users = userRepository.findAll()
                .stream()
                .map(UserConverter::convert)
                .toList();

        if (users.isEmpty()) {
            throw new UserDoesntExistException();
        }

        return GetAllUsersResponse.builder()
                .users(users)
                .build();
    }
}
