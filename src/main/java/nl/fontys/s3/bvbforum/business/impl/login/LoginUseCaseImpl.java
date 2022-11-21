package nl.fontys.s3.bvbforum.business.impl.login;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.Implementation;
import nl.fontys.s3.bvbforum.business.exception.login.InvalidCredentialsException;
import nl.fontys.s3.bvbforum.business.interfaces.login.AccessTokenEncoder;
import nl.fontys.s3.bvbforum.business.interfaces.login.LoginUseCase;
import nl.fontys.s3.bvbforum.domain.AccessToken;
import nl.fontys.s3.bvbforum.domain.request.login.LoginRequest;
import nl.fontys.s3.bvbforum.domain.response.login.LoginResponse;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new InvalidCredentialsException();
        }

        if (!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = generateAccessToken(user);
        return LoginResponse.builder().accessToken(accessToken).build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {
        Long userId = user.getId();
        List<String> roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().toString())
                .toList();

        return accessTokenEncoder.encode(
                AccessToken.builder()
                        .subject(user.getUsername())
                        .roles(roles)
                        .userId(userId)
                        .build()
        );
    }
}
