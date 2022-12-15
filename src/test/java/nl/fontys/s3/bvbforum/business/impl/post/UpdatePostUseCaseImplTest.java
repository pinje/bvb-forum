package nl.fontys.s3.bvbforum.business.impl.post;

import nl.fontys.s3.bvbforum.business.exception.post.PostDoesntExistException;
import nl.fontys.s3.bvbforum.domain.request.post.UpdatePostRequest;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePostUseCaseImplTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private UpdatePostUseCaseImpl updatePostUseCase;

    @Test
    void Update_ValidPost_TitleChanged() {
        // given
        PostEntity postEntity = PostEntity.builder()
                .id(1L)
                .title("title")
                .content("content")
                .build();

        // set up mock objects
        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(postEntity));
        when(postRepository.save(postEntity)).thenReturn(postEntity);

        // call the method
        UpdatePostRequest request = UpdatePostRequest.builder()
                .id(1L)
                .title("title_changed")
                .content(postEntity.getContent())
                .build();

        // when
        updatePostUseCase.updatePost(request);

        // then
        assertEquals("title_changed", postEntity.getTitle());
        assertEquals("content", postEntity.getContent());

        // verify
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(postEntity);
    }

    @Test
    void Update_ValidPost_ContentChanged() {
        // given
        PostEntity postEntity = PostEntity.builder()
                .id(1L)
                .title("title")
                .content("content")
                .build();

        // set up mock objects
        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(postEntity));
        when(postRepository.save(postEntity)).thenReturn(postEntity);

        // call the method
        UpdatePostRequest request = UpdatePostRequest.builder()
                .id(1L)
                .title(postEntity.getTitle())
                .content("content_changed")
                .build();

        // when
        updatePostUseCase.updatePost(request);

        // then
        assertEquals("title", postEntity.getTitle());
        assertEquals("content_changed", postEntity.getContent());

        // verify
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(postEntity);
    }

    @Test
    void Update_NonExistingPost_ThrowsException() throws PostDoesntExistException {
        // given
        UpdatePostRequest request = UpdatePostRequest.builder()
                .id(1L)
                .title("title")
                .content("content")
                .build();

        when(postRepository.findById(1L)).thenThrow(new PostDoesntExistException());

        // when
        ResponseStatusException exception = assertThrows(PostDoesntExistException.class, () -> {
            updatePostUseCase.updatePost(request);
        });

        // then
        assertEquals("POST_DOESNT_EXIST", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        // verify
        verify(postRepository, times(1)).findById(1L);
    }
}