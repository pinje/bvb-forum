package nl.fontys.s3.bvbforum.configuration.db;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.VoteRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import nl.fontys.s3.bvbforum.persistence.entity.VoteEntity;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;

@Component
@AllArgsConstructor
public class DatabaseDataInitializer {
    private UserRepository userRepository;

    private PostRepository postRepository;

    private VoteRepository voteRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseInitialDummyData() {
        if (userRepository.count() == 0) {
            userRepository.save(UserEntity.builder().username("user1").password("pwd1").build());
            userRepository.save(UserEntity.builder().username("user2").password("pwd2").build());
            userRepository.save(UserEntity.builder().username("user3").password("pwd3").build());
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
                            .user(userRepository.findByUsername("user1")).build());
        }

        if (voteRepository.count() == 0) {
            voteRepository.save(VoteEntity.builder()
                    .type(Boolean.TRUE)
                    .user(userRepository.findByUsername("user1"))
                    .post(postRepository.findById(1L).stream().filter(postEntity -> postEntity.getId() == 1L).findFirst().orElse(null)).build());
        }
    }
}
