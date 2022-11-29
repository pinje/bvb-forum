package nl.fontys.s3.bvbforum.business.interfaces.comment;

import nl.fontys.s3.bvbforum.domain.request.comment.UpdateCommentRequest;

public interface UpdateCommentUseCase {
    void updateComment(UpdateCommentRequest request);
}
