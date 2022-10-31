package nl.fontys.s3.bvbforum.repository;

import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void save_shouldSaveUserWithAllFields() {
        UserEntity user = UserEntity.builder()
                .id(1L)
                .username("one")
                .build();

        UserEntity savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());

        savedUser = entityManager.find(UserEntity.class, savedUser.getId());

        UserEntity expectedUser = UserEntity.builder()
                .id(savedUser.getId())
                .username("one")
                .build();

        assertEquals(expectedUser, savedUser);
    }
}