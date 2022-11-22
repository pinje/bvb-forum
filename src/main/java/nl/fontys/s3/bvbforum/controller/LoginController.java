package nl.fontys.s3.bvbforum.controller;

import lombok.RequiredArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.login.LoginUseCase;
import nl.fontys.s3.bvbforum.domain.request.login.LoginRequest;
import nl.fontys.s3.bvbforum.domain.response.login.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginUseCase loginUseCase;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = loginUseCase.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
