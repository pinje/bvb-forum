package nl.fontys.s3.bvbforum.persistence.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rating")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @NotNull
    @Column(name = "rating")
    private Long rating;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "rating_post_id")
    private RatingPostEntity ratingPost;
}
