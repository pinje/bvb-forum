package nl.fontys.s3.bvbforum.business.impl.post;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.post.GetPostUseCase;
import nl.fontys.s3.bvbforum.business.interfaces.user.GetUserUseCase;
import nl.fontys.s3.bvbforum.domain.PostInformationDTO;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetPostUseCaseImpl implements GetPostUseCase {
    private PostRepository postRepository;

    @Override
    public PostInformationDTO getPostById(long postId) {
        return postRepository.findById(postId)
                .stream()
                .map(PostConverter::convert)
                .findFirst()
                .orElse(null);
    }
}
