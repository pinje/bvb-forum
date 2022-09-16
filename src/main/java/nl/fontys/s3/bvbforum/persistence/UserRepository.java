package nl.fontys.s3.bvbforum.persistence;

import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;

import java.util.List;

public interface UserRepository {
    int count();

    UserEntity save(UserEntity user);
    UserEntity update(UserEntity user);

    boolean existsByUsername(String username);

    List<UserEntity> findAll();

    UserEntity findById(long userId);

    void deleteById(long userId);
}
