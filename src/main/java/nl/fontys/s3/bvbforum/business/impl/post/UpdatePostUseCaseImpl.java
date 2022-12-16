package nl.fontys.s3.bvbforum.business.impl.post;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.exception.UnauthorizedDataAccessException;
import nl.fontys.s3.bvbforum.business.exception.post.PostDoesntExistException;
import nl.fontys.s3.bvbforum.business.interfaces.post.UpdatePostUseCase;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.domain.request.post.UpdatePostRequest;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.RoleEnum;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdatePostUseCaseImpl implements UpdatePostUseCase {
    private PostRepository postRepository;
    private AccessToken accessToken;

    @Override
    public void updatePost(UpdatePostRequest request) {

        Optional<PostEntity> postOptional = postRepository.findById(request.getId());

        if (postOptional.isEmpty()) {
            throw new PostDoesntExistException();
        }

        PostEntity post = postOptional.get();

        if(!accessToken.hasRole(RoleEnum.ADMIN.name())) {
            if (accessToken.getUserId() != post.getUser().getId()) {
                throw new UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
            }
        }

        updateFields(request, post);
    }

    @Override
    public void upvote(long id) {
        Optional<PostEntity> postOptional = postRepository.findById(id);

        if (postOptional.isEmpty()) {
            throw new PostDoesntExistException();
        }

        PostEntity post = postOptional.get();
        addVote(post);
    }

    @Override
    public void downvote(long id) {
        Optional<PostEntity> postOptional = postRepository.findById(id);

        if (postOptional.isEmpty()) {
            throw new PostDoesntExistException();
        }

        PostEntity post = postOptional.get();
        subtractVote(post);
    }

    private void updateFields(UpdatePostRequest request, PostEntity post) {
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        postRepository.save(post);
    }

    private void addVote(PostEntity post) {
        post.setVote(post.getVote() + 1);
        postRepository.save(post);
    }

    private void subtractVote(PostEntity post) {
        post.setVote(post.getVote() - 1);
        postRepository.save(post);
    }
}
