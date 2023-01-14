package nl.fontys.s3.bvbforum.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "rating_post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Timestamp date;

    @NotNull
    @Column(name = "start_year")
    private Integer start_year;

    @NotNull
    @Column(name = "end_year")
    private Integer end_year;

    @NotNull
    @Column(name = "matchday")
    private Integer matchday;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "opponent")
    private String opponent;

    @NotNull
    @Column(name = "tournament")
    @Enumerated(EnumType.STRING)
    private TournamentEnum tournament;

    @OneToMany(mappedBy = "ratingPost")
    @JsonIgnore
    private List<RatingEntity> ratings;

    @OneToMany(mappedBy = "ratingPost")
    @JsonIgnore
    private List<RatingPostPlayerEntity> ratingPostPlayers;
}
