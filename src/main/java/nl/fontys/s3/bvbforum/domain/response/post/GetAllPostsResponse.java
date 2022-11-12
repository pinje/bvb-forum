package nl.fontys.s3.bvbforum.domain.response.post;

import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.bvbforum.domain.Post;
import nl.fontys.s3.bvbforum.domain.PostInformationDTO;

import java.util.List;

@Data
@Builder
public class GetAllPostsResponse {
    private List<PostInformationDTO> posts;
}
