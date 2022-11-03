package nl.fontys.s3.bvbforum.configuration.db;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseDataInitializer {
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseInitialDummyData() {
        if (userRepository.count() == 0) {
            userRepository.save(UserEntity.builder().username("user1").password("pwd1").build());
            userRepository.save(UserEntity.builder().username("user2").password("pwd2").build());
            userRepository.save(UserEntity.builder().username("user3").password("pwd3").build());
        }
    }
}
