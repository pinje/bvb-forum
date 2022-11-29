package nl.fontys.s3.bvbforum.persistence;

import nl.fontys.s3.bvbforum.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByPostId(Long postId);
    List<CommentEntity> findAllByUserId(long userId);
}
