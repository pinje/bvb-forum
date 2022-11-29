package nl.fontys.s3.bvbforum.business.impl.comment;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.comment.DeleteCommentUseCase;
import nl.fontys.s3.bvbforum.persistence.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteCommentUseCaseImpl implements DeleteCommentUseCase {
    private final CommentRepository commentRepository;

    @Override
    public void deleteComment(long commentId) { this.commentRepository.deleteById(commentId); }
}
