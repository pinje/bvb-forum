package nl.fontys.s3.bvbforum.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Length(min = 2, max = 100)
    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Set<UserRoleEntity> userRoles;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<PostEntity> posts;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<CommentEntity> comments;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<VoteEntity> votes;
}
