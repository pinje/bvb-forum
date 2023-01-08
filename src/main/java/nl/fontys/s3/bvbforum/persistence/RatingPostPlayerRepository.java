package nl.fontys.s3.bvbforum.persistence;

import nl.fontys.s3.bvbforum.persistence.entity.RatingPostPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingPostPlayerRepository extends JpaRepository<RatingPostPlayerEntity, Long> {
    List<RatingPostPlayerEntity> findRatingPostPlayerEntitiesByRatingPostId(long ratingPostId);
}
