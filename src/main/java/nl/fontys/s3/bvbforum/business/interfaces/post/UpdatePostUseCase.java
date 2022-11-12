package nl.fontys.s3.bvbforum.business.interfaces.post;

import nl.fontys.s3.bvbforum.domain.request.post.UpdatePostRequest;
import nl.fontys.s3.bvbforum.domain.request.post.UpdatePostVoteRequest;

public interface UpdatePostUseCase {
    void updatePost(UpdatePostRequest request);

    void upvote(UpdatePostVoteRequest request);

    void downvote(UpdatePostVoteRequest request);
}
