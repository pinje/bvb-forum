package nl.fontys.s3.bvbforum.domain.request.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentsRequest {
    // post id or user id
    private Long id;
}
