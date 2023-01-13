package nl.fontys.s3.bvbforum.business.impl.comment;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.comment.CreateCommentUseCase;
import nl.fontys.s3.bvbforum.domain.request.comment.CreateCommentRequest;
import nl.fontys.s3.bvbforum.domain.response.comment.CreateCommentResponse;
import nl.fontys.s3.bvbforum.persistence.CommentRepository;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.CommentEntity;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;

@Service
@AllArgsConstructor
public class CreateCommentUseCaseImpl implements CreateCommentUseCase {
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;

    @Transactional
    @Override
    public CreateCommentResponse createComment(CreateCommentRequest request) {
        CommentEntity saveComment = save(request);

        DateTime date = DateTime.now();
        Timestamp ts = new Timestamp(date.toDateTime().getMillis());

        saveComment.setDate(ts);

        return CreateCommentResponse.builder()
                .commentId(saveComment.getId())
                .build();
    }

    private CommentEntity save(CreateCommentRequest request) {

        CommentEntity newComment = CommentEntity.builder()
                .comment(request.getComment())
                .user(userRepository.findById(request.getUserId())
                        .stream()
                        .findFirst().orElse(null))
                .post(postRepository.findById(request.getPostId())
                        .stream()
                        .findFirst().orElse(null))
                .build();

        return commentRepository.save(newComment);
    }

}
