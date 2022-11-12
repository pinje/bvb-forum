package nl.fontys.s3.bvbforum.business.interfaces.user;

import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;

public interface GetUserUseCase {
    UserEntity getUserById(long userId);
    UserEntity getUserByUsername(String username);
}
