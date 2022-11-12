package nl.fontys.s3.bvbforum.business.impl.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.post.GetAllPostsUseCase;
import nl.fontys.s3.bvbforum.domain.Post;
import nl.fontys.s3.bvbforum.domain.PostInformationDTO;
import nl.fontys.s3.bvbforum.domain.response.post.GetAllPostsResponse;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllPostsUseCaseImpl implements GetAllPostsUseCase {
    private PostRepository postRepository;

    // return a list of post information DTO
    @Override
    public GetAllPostsResponse getAllPosts() {
        List<PostInformationDTO> posts = postRepository.findAll()
                .stream()
                .map(PostConverter::convert)
                .toList();

        return GetAllPostsResponse.builder()
                .posts(posts)
                .build();
    }
}
