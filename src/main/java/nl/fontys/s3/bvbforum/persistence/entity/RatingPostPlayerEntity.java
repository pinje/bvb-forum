package nl.fontys.s3.bvbforum.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "rating_post_player")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingPostPlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerEntity player;

    @ManyToOne
    @JoinColumn(name = "rating_post_id")
    private RatingPostEntity ratingPost;
}
