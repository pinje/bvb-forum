package nl.fontys.s3.bvbforum.business.impl.user;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.UnauthorizedDataAccessException;
import nl.fontys.s3.bvbforum.business.exception.user.UserDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.user.DeleteUserUseCase;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.RoleEnum;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    private final UserRepository userRepository;
    private AccessToken accessToken;

    @Override
    public void deleteUser(long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserDoesntExistException();
        }

        UserEntity user = userOptional.get();

        if(!accessToken.hasRole(RoleEnum.ADMIN.name())) {
            if (accessToken.getUserId() != user.getId()) {
                throw new UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
            }
        }

        this.userRepository.deleteById(userId);
    }
}
