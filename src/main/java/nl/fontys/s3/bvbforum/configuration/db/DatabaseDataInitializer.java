package nl.fontys.s3.bvbforum.configuration.db;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.persistence.*;
import nl.fontys.s3.bvbforum.persistence.entity.*;
import org.joda.time.DateTime;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class DatabaseDataInitializer {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private VoteRepository voteRepository;
    private PasswordEncoder passwordEncoder;
    private CommentRepository commentRepository;
    private PlayerRepository playerRepository;
    private RatingRepository ratingRepository;
    private RatingPostRepository ratingPostRepository;
    private RatingPostPlayerRepository ratingPostPlayerRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseInitialDummyData() {
        if (isDatabaseEmpty()) {
            insertAdminUser();
            insertMemberUser();
            insertPost();
            insertVote();
            insertComment();
            insertPlayer();
            insertRatingPost();
            insertRatingPostPlayer();
            insertRating();
        }
    }

    private boolean isDatabaseEmpty() {
        return userRepository.count() == 0;
    }

    private void insertAdminUser() {
        UserEntity adminUser = UserEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode("123"))
                .build();
        UserRoleEntity adminRole = UserRoleEntity.builder().role(RoleEnum.ADMIN).user(adminUser).build();
        adminUser.setUserRoles(Set.of(adminRole));
        userRepository.save(adminUser);
    }

    private void insertMemberUser() {
        UserEntity memberUser = UserEntity.builder()
                .username("user")
                .password(passwordEncoder.encode("123"))
                .build();
        UserRoleEntity memberRole = UserRoleEntity.builder().role(RoleEnum.MEMBER).user(memberUser).build();
        memberUser.setUserRoles(Set.of(memberRole));
        userRepository.save(memberUser);
    }

    public void insertPost() {
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

    public void insertVote() {
        voteRepository.save(VoteEntity.builder()
                .type(Boolean.TRUE)
                .user(userRepository.findByUsername("admin"))
                .post(postRepository.findById(1L).stream().filter(postEntity -> postEntity.getId() == 1L).findFirst().orElse(null)).build());
    }

    private void insertComment() {
        DateTime date = DateTime.now();
        Timestamp ts = new Timestamp(date.toDateTime().getMillis());
        commentRepository.save(
                CommentEntity.builder()
                        .date(ts)
                        .comment("this is a comment")
                        .user(userRepository.findByUsername("admin"))
                        .post(postRepository.findById(1L).stream().filter(postEntity -> postEntity.getId() == 1L).findFirst().orElse(null)).build()
        );
    }

    private void insertPlayer() {
        PlayerEntity player = PlayerEntity.builder()
                .firstname("marco")
                .lastname("reus")
                .position(PositionEnum.MF)
                .build();

        playerRepository.save(player);
    }

    private void insertRatingPost() {
        DateTime date = DateTime.now();
        Timestamp ts = new Timestamp(date.toDateTime().getMillis());
        RatingPostEntity ratingPost = RatingPostEntity.builder()
                .date(ts)
                .start_year(2022)
                .end_year(2023)
                .matchday(1)
                .opponent("Schalke04")
                .tournament(TournamentEnum.BUNDESLIGA)
                .build();

        RatingPostEntity ratingPostTwo = RatingPostEntity.builder()
                .date(ts)
                .start_year(2022)
                .end_year(2023)
                .matchday(0)
                .opponent("Real Madrid")
                .tournament(TournamentEnum.CHAMPIONS_LEAGUE)
                .build();

        RatingPostEntity ratingPostThree = RatingPostEntity.builder()
                .date(ts)
                .start_year(2022)
                .end_year(2023)
                .matchday(0)
                .opponent("St. Pauli")
                .tournament(TournamentEnum.DFB_CUP)
                .build();

        ratingPostRepository.save(ratingPost);
        ratingPostRepository.save(ratingPostTwo);
        ratingPostRepository.save(ratingPostThree);
    }

    private void insertRatingPostPlayer() {
        PlayerEntity player = playerRepository.findById(1L).orElseThrow();
        RatingPostEntity ratingPost = ratingPostRepository.findById(1L).orElseThrow();

        RatingPostPlayerEntity ratingPostPlayer = RatingPostPlayerEntity.builder()
                .player(player)
                .ratingPost(ratingPost)
                .build();

        ratingPostPlayerRepository.save(ratingPostPlayer);
    }

    private void insertRating() {
        RatingEntity rating = RatingEntity.builder()
                .player(playerRepository.findByLastname("reus"))
                .rating(10L)
                .user(userRepository.findByUsername("admin"))
                .ratingPost(ratingPostRepository.findById(1L).stream().findFirst().orElse(null))
                .build();

        RatingEntity ratingTwo = RatingEntity.builder()
                .player(playerRepository.findByLastname("reus"))
                .rating(7L)
                .user(userRepository.findByUsername("admin"))
                .ratingPost(ratingPostRepository.findById(2L).stream().findFirst().orElse(null))
                .build();

        RatingEntity ratingThree = RatingEntity.builder()
                .player(playerRepository.findByLastname("reus"))
                .rating(5L)
                .user(userRepository.findByUsername("admin"))
                .ratingPost(ratingPostRepository.findById(3L).stream().findFirst().orElse(null))
                .build();

        ratingRepository.save(rating);
        ratingRepository.save(ratingTwo);
        ratingRepository.save(ratingThree);
    }
}
