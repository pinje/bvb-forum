package nl.fontys.s3.bvbforum.persistence;

import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import nl.fontys.s3.bvbforum.persistence.entity.PositionEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    PlayerEntity findByLastname(String lastname);
    List<PlayerEntity> findAllByPosition(PositionEnum position);
}
