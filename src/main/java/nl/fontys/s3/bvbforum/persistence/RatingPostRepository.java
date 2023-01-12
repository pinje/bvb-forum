package nl.fontys.s3.bvbforum.persistence;

import nl.fontys.s3.bvbforum.persistence.entity.RatingPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingPostRepository extends JpaRepository<RatingPostEntity, Long> {
    RatingPostEntity findFirstByOrderByDateDesc();
}
