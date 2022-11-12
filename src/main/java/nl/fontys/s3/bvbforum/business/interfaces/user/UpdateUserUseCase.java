package nl.fontys.s3.bvbforum.business.interfaces.user;

import nl.fontys.s3.bvbforum.domain.request.user.UpdateUserRequest;

public interface UpdateUserUseCase {
    void updateUser(UpdateUserRequest request);
}
