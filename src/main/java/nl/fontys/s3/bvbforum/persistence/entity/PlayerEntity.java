package nl.fontys.s3.bvbforum.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "player")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "firstname")
    private String firstname;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "lastname")
    private String lastname;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private PositionEnum position;
}
