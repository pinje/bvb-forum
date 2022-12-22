package nl.fontys.s3.bvbforum.business.impl.comment;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.UnauthorizedDataAccessException;
import nl.fontys.s3.bvbforum.business.exception.comment.CommentDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.comment.DeleteCommentUseCase;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.persistence.CommentRepository;
import nl.fontys.s3.bvbforum.persistence.entity.CommentEntity;
import nl.fontys.s3.bvbforum.persistence.entity.RoleEnum;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteCommentUseCaseImpl implements DeleteCommentUseCase {
    private final CommentRepository commentRepository;
    private AccessToken accessToken;

    @Override
    public void deleteComment(long commentId) {
        Optional<CommentEntity> commentOptional = commentRepository.findById(commentId);

        if (commentOptional.isEmpty()) {
            throw new CommentDoesntExistException();
        }

        CommentEntity comment = commentOptional.get();

        if (!accessToken.hasRole(RoleEnum.ADMIN.name())) {
            if (accessToken.getUserId() != comment.getUser().getId()) {
                throw new UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
            }
        }

        this.commentRepository.deleteById(commentId);
    }
}
