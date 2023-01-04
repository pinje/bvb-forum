package nl.fontys.s3.bvbforum.persistence;

import nl.fontys.s3.bvbforum.persistence.entity.RatingPostPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingPostPlayerRepository extends JpaRepository<RatingPostPlayerEntity, Long> {
}
