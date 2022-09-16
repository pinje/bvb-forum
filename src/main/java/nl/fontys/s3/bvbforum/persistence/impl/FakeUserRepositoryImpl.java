package nl.fontys.s3.bvbforum.persistence.impl;

import nl.fontys.s3.bvbforum.domain.User;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class FakeUserRepositoryImpl implements UserRepository {

    private static long NEXT_ID = 1;

    private final List<UserEntity> savedUsers;

    public FakeUserRepositoryImpl() {
        this.savedUsers = new ArrayList<>();
    }

    @Override
    public int count() { return this.savedUsers.size(); }

    @Override
    public UserEntity save(UserEntity user) {
        user.setId(NEXT_ID);
        NEXT_ID++;
        this.savedUsers.add(user);
        return user;
    }

    @Override
    public UserEntity update(UserEntity user) {
        user.setUsername(user.getUsername());
        this.savedUsers.set(Math.toIntExact(user.getId())-1, user);
        return user;
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.savedUsers
                .stream()
                .anyMatch(userEntity -> userEntity.getUsername() == username);
    }

    @Override
    public List<UserEntity> findAll() {
        return Collections.unmodifiableList(savedUsers);
    }

    @Override
    public UserEntity findById(long userId) {
        return this.savedUsers
                .stream()
                .filter(userEntity -> userEntity.getId() == userId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(long userId) {
        this.savedUsers.removeIf(userEntity -> userEntity.getId().equals(userId));
    }

}
