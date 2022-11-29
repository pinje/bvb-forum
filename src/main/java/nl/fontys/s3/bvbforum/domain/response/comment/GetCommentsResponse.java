package nl.fontys.s3.bvbforum.domain.response.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.fontys.s3.bvbforum.domain.CommentInformationDTO;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentsResponse {
    private List<CommentInformationDTO> comments;
}
