package nl.fontys.s3.bvbforum.domain.response.user;

import lombok.Builder;
import lombok.Data;
import nl.fontys.s3.bvbforum.domain.User;

import java.util.List;

@Data
@Builder
public class GetAllUsersResponse {
    private List<User> users;
}
