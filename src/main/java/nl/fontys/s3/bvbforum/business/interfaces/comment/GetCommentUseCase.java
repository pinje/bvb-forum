package nl.fontys.s3.bvbforum.business.interfaces.comment;

import nl.fontys.s3.bvbforum.domain.CommentInformationDTO;

public interface GetCommentUseCase {
    CommentInformationDTO getCommentById(long commentId);
}
