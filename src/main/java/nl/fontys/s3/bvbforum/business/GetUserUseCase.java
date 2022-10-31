package nl.fontys.s3.bvbforum.business;

import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;

import java.util.Optional;

public interface GetUserUseCase {
    UserEntity getUserById(long userId);
    UserEntity getUserByUsername(String username);
}
