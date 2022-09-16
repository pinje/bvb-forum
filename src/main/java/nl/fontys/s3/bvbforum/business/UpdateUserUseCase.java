package nl.fontys.s3.bvbforum.business;

import nl.fontys.s3.bvbforum.domain.request.UpdateUserRequest;

public interface UpdateUserUseCase {
    void updateUser(UpdateUserRequest request);
}
