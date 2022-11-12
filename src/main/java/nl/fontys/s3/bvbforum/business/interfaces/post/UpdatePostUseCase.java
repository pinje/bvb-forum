package nl.fontys.s3.bvbforum.business.interfaces.post;

import nl.fontys.s3.bvbforum.domain.request.post.UpdatePostRequest;

public interface UpdatePostUseCase {
    void updatePost(UpdatePostRequest request);

    void upvote(long id);

    void downvote(long id);
}
