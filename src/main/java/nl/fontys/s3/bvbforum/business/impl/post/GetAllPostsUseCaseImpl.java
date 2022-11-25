package nl.fontys.s3.bvbforum.business.impl.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.post.GetAllPostsUseCase;
import nl.fontys.s3.bvbforum.domain.Post;
import nl.fontys.s3.bvbforum.domain.PostInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.post.GetAllPostsRequest;
import nl.fontys.s3.bvbforum.domain.response.post.GetAllPostsResponse;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllPostsUseCaseImpl implements GetAllPostsUseCase {
    private PostRepository postRepository;

    // return a list of post information DTO
    @Override
    public GetAllPostsResponse getAllPosts(final GetAllPostsRequest request) {
        List<PostEntity> results;
        if (request.getUserId() instanceof Long) {
            results = postRepository.findAllByUserId(request.getUserId());
        } else {
            results = postRepository.findAll();
        }

        final GetAllPostsResponse response = new GetAllPostsResponse();

        List<PostInformationDTO> posts = results
                .stream()
                .map(PostConverter::convert)
                .toList();

        response.setPosts(posts);

        return response;
    }
}
