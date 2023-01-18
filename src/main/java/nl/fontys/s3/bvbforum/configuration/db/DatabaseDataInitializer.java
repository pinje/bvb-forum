package nl.fontys.s3.bvbforum.configuration.db;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.persistence.*;
import nl.fontys.s3.bvbforum.persistence.entity.*;
import org.joda.time.DateTime;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
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
            insertForwards();
            insertMidfielders();
            insertDefenders();
            insertGoalkeepers();
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
                        .title("Welcome")
                        .content("Welcome everybody to the unofficial BVB Forum!")
                        .vote(1L)
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
                        .comment("Hope you enjoy it")
                        .user(userRepository.findByUsername("admin"))
                        .post(postRepository.findById(1L).stream().filter(postEntity -> postEntity.getId() == 1L).findFirst().orElse(null)).build()
        );
    }

    private void insertForwards() {
        PlayerEntity malen = PlayerEntity.builder()
                .firstname("donyell")
                .lastname("malen")
                .position(PositionEnum.FW)
                .build();

        PlayerEntity moukoko = PlayerEntity.builder()
                .firstname("youssoufa")
                .lastname("moukoko")
                .position(PositionEnum.FW)
                .build();
        PlayerEntity modeste = PlayerEntity.builder()
                .firstname("anthony")
                .lastname("modeste")
                .position(PositionEnum.FW)
                .build();
        PlayerEntity jamie = PlayerEntity.builder()
                .firstname("jamie")
                .lastname("bynoe-gittens")
                .position(PositionEnum.FW)
                .build();
        PlayerEntity brandt = PlayerEntity.builder()
                .firstname("julian")
                .lastname("brandt")
                .position(PositionEnum.FW)
                .build();

        PlayerEntity adeyemi = PlayerEntity.builder()
                .firstname("karim")
                .lastname("adeyemi")
                .position(PositionEnum.FW)
                .build();

        PlayerEntity reyna = PlayerEntity.builder()
                .firstname("gio")
                .lastname("reyna")
                .position(PositionEnum.FW)
                .build();

        PlayerEntity hazard = PlayerEntity.builder()
                .firstname("thorgan")
                .lastname("hazard")
                .position(PositionEnum.FW)
                .build();

        PlayerEntity justin = PlayerEntity.builder()
                .firstname("justin")
                .lastname("njinmah")
                .position(PositionEnum.FW)
                .build();

        playerRepository.save(malen);
        playerRepository.save(moukoko);
        playerRepository.save(modeste);
        playerRepository.save(jamie);
        playerRepository.save(brandt);
        playerRepository.save(adeyemi);
        playerRepository.save(reyna);
        playerRepository.save(hazard);
        playerRepository.save(justin);
    }

    private void insertMidfielders() {
        PlayerEntity jude = PlayerEntity.builder()
                .firstname("jude")
                .lastname("bellingham")
                .position(PositionEnum.MF)
                .build();

        PlayerEntity ozcan = PlayerEntity.builder()
                .firstname("salih")
                .lastname("ozcan")
                .position(PositionEnum.MF)
                .build();

        PlayerEntity marco = PlayerEntity.builder()
                .firstname("marco")
                .lastname("reus")
                .position(PositionEnum.MF)
                .build();

        PlayerEntity mo = PlayerEntity.builder()
                .firstname("mahmoud")
                .lastname("dahoud")
                .position(PositionEnum.MF)
                .build();

        PlayerEntity pasalic = PlayerEntity.builder()
                .firstname("marco")
                .lastname("pasalic")
                .position(PositionEnum.MF)
                .build();

        PlayerEntity kamara = PlayerEntity.builder()
                .firstname("abdoulaye")
                .lastname("kamara")
                .position(PositionEnum.MF)
                .build();

        PlayerEntity can = PlayerEntity.builder()
                .firstname("emre")
                .lastname("can")
                .position(PositionEnum.MF)
                .build();

        playerRepository.save(jude);
        playerRepository.save(ozcan);
        playerRepository.save(marco);
        playerRepository.save(mo);
        playerRepository.save(pasalic);
        playerRepository.save(kamara);
        playerRepository.save(can);
    }

    private void insertDefenders() {
        PlayerEntity wolf = PlayerEntity.builder()
                .firstname("marius")
                .lastname("wolf")
                .position(PositionEnum.DF)
                .build();

        PlayerEntity schlotterbeck = PlayerEntity.builder()
                .firstname("nico")
                .lastname("schlotterbeck")
                .position(PositionEnum.DF)
                .build();

        PlayerEntity hummels = PlayerEntity.builder()
                .firstname("mats")
                .lastname("hummels")
                .position(PositionEnum.DF)
                .build();

        PlayerEntity rapha = PlayerEntity.builder()
                .firstname("raphael")
                .lastname("guerreiro")
                .position(PositionEnum.DF)
                .build();

        PlayerEntity sule = PlayerEntity.builder()
                .firstname("niklas")
                .lastname("sule")
                .position(PositionEnum.DF)
                .build();

        PlayerEntity meunier = PlayerEntity.builder()
                .firstname("thomas")
                .lastname("meunier")
                .position(PositionEnum.DF)
                .build();

        PlayerEntity passlack = PlayerEntity.builder()
                .firstname("felix")
                .lastname("passlack")
                .position(PositionEnum.DF)
                .build();

        PlayerEntity rothe = PlayerEntity.builder()
                .firstname("tom")
                .lastname("rothe")
                .position(PositionEnum.DF)
                .build();

        PlayerEntity papadopoulos = PlayerEntity.builder()
                .firstname("antonios")
                .lastname("papadopoulos")
                .position(PositionEnum.DF)
                .build();

        PlayerEntity coulibaly = PlayerEntity.builder()
                .firstname("soumaila")
                .lastname("coulibaly")
                .position(PositionEnum.DF)
                .build();

        playerRepository.save(wolf);
        playerRepository.save(schlotterbeck);
        playerRepository.save(hummels);
        playerRepository.save(rapha);
        playerRepository.save(sule);
        playerRepository.save(meunier);
        playerRepository.save(passlack);
        playerRepository.save(rothe);
        playerRepository.save(papadopoulos);
        playerRepository.save(coulibaly);
    }

    private void insertGoalkeepers() {
        PlayerEntity kobel = PlayerEntity.builder()
                .firstname("gregor")
                .lastname("kobel")
                .position(PositionEnum.GK)
                .build();

        PlayerEntity meyer = PlayerEntity.builder()
                .firstname("alexander")
                .lastname("meyer")
                .position(PositionEnum.GK)
                .build();

        PlayerEntity lotka = PlayerEntity.builder()
                .firstname("marcel")
                .lastname("lotka")
                .position(PositionEnum.GK)
                .build();

        playerRepository.save(kobel);
        playerRepository.save(meyer);
        playerRepository.save(lotka);
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
