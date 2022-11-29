package nl.fontys.s3.bvbforum.business.interfaces.comment;

import nl.fontys.s3.bvbforum.domain.request.comment.GetCommentsRequest;
import nl.fontys.s3.bvbforum.domain.response.comment.GetCommentsResponse;

public interface GetCommentsUseCase {
    GetCommentsResponse getCommentsByPostId(GetCommentsRequest request);
    GetCommentsResponse getCommentsByUserId(GetCommentsRequest request);
}
