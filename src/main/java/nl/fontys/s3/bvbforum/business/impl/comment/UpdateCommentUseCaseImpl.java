package nl.fontys.s3.bvbforum.business.impl.comment;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.comment.CommentDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.comment.UpdateCommentUseCase;
import nl.fontys.s3.bvbforum.domain.request.comment.UpdateCommentRequest;
import nl.fontys.s3.bvbforum.persistence.CommentRepository;
import nl.fontys.s3.bvbforum.persistence.entity.CommentEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateCommentUseCaseImpl implements UpdateCommentUseCase {
    private CommentRepository commentRepository;

    @Override
    public void updateComment(UpdateCommentRequest request) {
        Optional<CommentEntity> commentOptional = commentRepository.findById(request.getId());

        if (commentOptional.isEmpty()) {
            throw new CommentDoesntExistException();
        }

        CommentEntity comment = commentOptional.get();
        updateFields(request, comment);
    }

    private void updateFields(UpdateCommentRequest request, CommentEntity comment) {
        comment.setComment(request.getComment());
        commentRepository.save(comment);
    }
}
