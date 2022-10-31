package nl.fontys.s3.bvbforum.business.impl;

import nl.fontys.s3.bvbforum.domain.request.UpdateUserRequest;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    @Test
    void updateUser() {
        UserEntity user = UserEntity.builder()
                .id(111L)
                .username("Shuhei")
                .build();
        userRepository.save(user);

        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(user.getId())
                .username("Fontys")
                .build();

        updateUserUseCase.updateUser(request);

        assertEquals("Fontys", user.getUsername());
    }

}