package nl.fontys.s3.bvbforum.business.interfaces.comment;

import nl.fontys.s3.bvbforum.domain.request.comment.CreateCommentRequest;
import nl.fontys.s3.bvbforum.domain.response.comment.CreateCommentResponse;

public interface CreateCommentUseCase {
    CreateCommentResponse createComment(CreateCommentRequest request);
}
