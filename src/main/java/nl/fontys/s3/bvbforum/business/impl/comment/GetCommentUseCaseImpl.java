package nl.fontys.s3.bvbforum.business.impl.comment;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.comment.GetCommentUseCase;
import nl.fontys.s3.bvbforum.domain.CommentInformationDTO;
import nl.fontys.s3.bvbforum.persistence.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetCommentUseCaseImpl implements GetCommentUseCase {
    private CommentRepository commentRepository;

    @Override
    public CommentInformationDTO getCommentById(long commentId) {
        return commentRepository.findById(commentId)
                .stream()
                .map(CommentConverter::convert)
                .findFirst()
                .orElse(null);
    }
}
