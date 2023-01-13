package nl.fontys.s3.bvbforum.business.impl.post;

import nl.fontys.s3.bvbforum.domain.PostInformationDTO;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPostUseCaseImplTest {
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private GetPostUseCaseImpl getPostUseCase;

    @Test
    void Get_PostById_ReturnsPost() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123456")
                .build();

        PostEntity postEntity = PostEntity.builder()
                .id(1L)
                .title("title")
                .user(userEntity)
                .build();

        PostInformationDTO expectedPost = PostInformationDTO.builder()
                .id(1L)
                .title("title")
                .userId("1")
                .username("Shuhei")
                .build();

        // set up mock objects
        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(postEntity));

        // when
        PostInformationDTO actualPost = getPostUseCase.getPostById(1L);

        // then
        assertNotNull(actualPost);
        assertEquals(expectedPost, actualPost);

        // verify
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    void Get_PostById_PostNotFound() {
        // given
        long nonExistentPostId = -1;

        // set up mock objects
        when(postRepository.findById(nonExistentPostId)).thenReturn(Optional.empty());

        // when
        PostInformationDTO result = getPostUseCase.getPostById(nonExistentPostId);

        // then
        assertNull(result);

        // verify
        verify(postRepository, times(1)).findById(nonExistentPostId);
    }
}