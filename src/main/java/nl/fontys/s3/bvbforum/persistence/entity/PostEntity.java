package nl.fontys.s3.bvbforum.persistence.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {
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

    @NotBlank
    @Length(min = 2, max = 500)
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "vote")
    private Long vote;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserEntity user;
}
