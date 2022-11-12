package nl.fontys.s3.bvbforum.business.interfaces.post;

import nl.fontys.s3.bvbforum.domain.response.post.GetAllPostsResponse;

public interface GetAllPostsUseCase {
    GetAllPostsResponse getAllPosts();
}
