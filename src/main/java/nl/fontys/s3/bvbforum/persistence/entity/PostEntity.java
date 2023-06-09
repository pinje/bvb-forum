package nl.fontys.s3.bvbforum.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;


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
    private UserEntity user;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<VoteEntity> votes;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<CommentEntity> comments;
}
