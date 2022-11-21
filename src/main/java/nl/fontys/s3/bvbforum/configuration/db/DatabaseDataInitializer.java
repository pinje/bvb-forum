package nl.fontys.s3.bvbforum.configuration.db;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.user.CreateUserUseCase;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.VoteRepository;
import nl.fontys.s3.bvbforum.persistence.entity.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Component
@AllArgsConstructor
public class DatabaseDataInitializer {
    private UserRepository userRepository;

    private PostRepository postRepository;

    private VoteRepository voteRepository;

    private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseInitialDummyData() {
        if (isDatabaseEmpty()) {
            insertAdminUser();
        }

        if (postRepository.count() == 0) {
            DateTime date = DateTime.now();
            Timestamp ts = new Timestamp(date.toDateTime().getMillis());
            postRepository.save(
                    PostEntity.builder()
                            .date(ts)
                            .title("title")
                            .content("content")
                            .vote(0L)
                            .user(userRepository.findByUsername("admin")).build());
        }

        if (voteRepository.count() == 0) {
            voteRepository.save(VoteEntity.builder()
                    .type(Boolean.TRUE)
                    .user(userRepository.findByUsername("admin"))
                    .post(postRepository.findById(1L).stream().filter(postEntity -> postEntity.getId() == 1L).findFirst().orElse(null)).build());
        }
    }

    private boolean isDatabaseEmpty() {
        return userRepository.count() == 0;
    }

    private void insertAdminUser() {
        UserEntity adminUser = UserEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode("test123"))
                .build();
        UserRoleEntity adminRole = UserRoleEntity.builder().role(RoleEnum.ADMIN).user(adminUser).build();
        adminUser.setUserRoles(Set.of(adminRole));
        userRepository.save(adminUser);
    }
}
