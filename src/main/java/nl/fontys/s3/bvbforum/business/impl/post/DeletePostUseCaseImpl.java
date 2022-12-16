package nl.fontys.s3.bvbforum.business.impl.post;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.UnauthorizedDataAccessException;
import nl.fontys.s3.bvbforum.business.exception.post.PostDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.post.DeletePostUseCase;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.RoleEnum;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeletePostUseCaseImpl implements DeletePostUseCase {
    private final PostRepository postRepository;
    private AccessToken accessToken;

    @Override
    public void deletePost(long postId) {
        Optional<PostEntity> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            throw new PostDoesntExistException();
        }

        PostEntity post = postOptional.get();

        if(!accessToken.hasRole(RoleEnum.ADMIN.name())) {
            if (accessToken.getUserId() != post.getUser().getId()) {
                throw new UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
            }
        }

        this.postRepository.deleteById(postId);
    }
}
