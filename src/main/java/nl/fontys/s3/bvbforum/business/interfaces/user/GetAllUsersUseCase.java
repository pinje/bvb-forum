package nl.fontys.s3.bvbforum.business.interfaces.user;

import nl.fontys.s3.bvbforum.domain.response.user.GetAllUsersResponse;

public interface GetAllUsersUseCase {
    GetAllUsersResponse getAllUsers();
}
