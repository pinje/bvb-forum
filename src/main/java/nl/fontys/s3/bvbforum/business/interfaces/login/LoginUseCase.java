package nl.fontys.s3.bvbforum.business.interfaces.login;

import nl.fontys.s3.bvbforum.domain.request.login.LoginRequest;
import nl.fontys.s3.bvbforum.domain.response.login.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);
}
