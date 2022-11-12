package nl.fontys.s3.bvbforum.business.interfaces.post;

import nl.fontys.s3.bvbforum.domain.PostInformationDTO;

public interface GetPostUseCase {
    PostInformationDTO getPostById(long postId);
}
