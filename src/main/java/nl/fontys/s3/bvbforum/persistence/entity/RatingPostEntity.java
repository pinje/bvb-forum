package nl.fontys.s3.bvbforum.persistence.entity;

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

    @NotNull
    @Column(name = "date")
    private Timestamp date;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "rating_post")
    private List<PlayerEntity> players;
}
