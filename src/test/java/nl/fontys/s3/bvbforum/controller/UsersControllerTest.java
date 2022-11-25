package nl.fontys.s3.bvbforum.controller;

import nl.fontys.s3.bvbforum.business.interfaces.user.CreateUserUseCase;
import nl.fontys.s3.bvbforum.domain.request.user.CreateUserRequest;
import nl.fontys.s3.bvbforum.domain.response.user.CreateUserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @Test
    void createUser_ShouldReturn201_whenRequestIsValid() throws Exception {
        CreateUserRequest expectedRequest = CreateUserRequest.builder()
                .username("Shuhei")
                .password("test123")
                .build();

        when(createUserUseCase.createUser(expectedRequest)).thenReturn(CreateUserResponse.builder()
                .userId(1L)
                .build());

        mockMvc.perform(post("/users")
                .contentType(APPLICATION_JSON_VALUE)
                .content("""
                        {
                            "username": "Shuhei",
                            "password": "test123"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                                                        {"userId":  1}
                                                    """));
        verify(createUserUseCase).createUser(expectedRequest);
    }

    @Test
    @WithMockUser(username = "Shuhei", roles = {"ADMIN"})
    void getAllUsers_shouldReturn200WithUsersList() throws Exception {
        
    }
}