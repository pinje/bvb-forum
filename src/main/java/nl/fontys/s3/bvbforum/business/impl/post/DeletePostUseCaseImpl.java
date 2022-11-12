package nl.fontys.s3.bvbforum.business.impl.post;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.post.DeletePostUseCase;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeletePostUseCaseImpl implements DeletePostUseCase {
    private final PostRepository postRepository;

    @Override
    public void deletePost(long postId) { this.postRepository.deleteById(postId);}
}
