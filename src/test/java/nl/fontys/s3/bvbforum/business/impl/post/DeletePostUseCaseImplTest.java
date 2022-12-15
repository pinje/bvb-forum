package nl.fontys.s3.bvbforum.business.impl.post;

import nl.fontys.s3.bvbforum.business.exception.post.PostDoesntExistException;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePostUseCaseImplTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private DeletePostUseCaseImpl deletePostUseCase;

    @Test
    void Delete_ExistingPost_ReturnsEmpty() {
        // given
        PostEntity postEntity = PostEntity.builder()
                .id(1L)
                .title("title")
                .content("content")
                .build();

        // when
        deletePostUseCase.deletePost(postEntity.getId());

        // verify
        verify(postRepository, times(1)).deleteById(1L);
    }

    @Test
    void Delete_NonExistingPost_ThrowsException() throws PostDoesntExistException {
        // given
        PostEntity postEntity = PostEntity.builder()
                .id(1L)
                .title("title")
                .content("content")
                .build();

        Long id = postEntity.getId();

        // set up mock objects
        doThrow(new PostDoesntExistException()).when(postRepository).deleteById(1L);

        // when
        ResponseStatusException exception = assertThrows(PostDoesntExistException.class, () -> {
            deletePostUseCase.deletePost(id);
        });

        // then
        assertEquals("POST_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(postRepository, times(1)).deleteById(1L);
    }

}