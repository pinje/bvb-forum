package nl.fontys.s3.bvbforum.business.impl.post;

import nl.fontys.s3.bvbforum.domain.PostInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.post.GetAllPostsRequest;
import nl.fontys.s3.bvbforum.domain.response.post.GetAllPostsResponse;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllPostsUseCaseImplTest {
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private GetAllPostsUseCaseImpl getAllPostsUseCase;

    @Test
    void GetAll_ExistingPosts_ReturnsAllPosts_withoutUserId() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123456")
                .build();

        PostEntity postEntityOne = PostEntity.builder()
                .id(1L)
                .title("titleOne")
                .content("contentOne")
                .vote(1L)
                .user(userEntity)
                .build();

        PostEntity postEntityTwo = PostEntity.builder()
                .id(2L)
                .title("titleTwo")
                .content("contentTwo")
                .vote(3L)
                .user(userEntity)
                .build();

        // set up mock objects
        when(postRepository.findAll()).thenReturn(List.of(postEntityOne, postEntityTwo));

        // when
        GetAllPostsResponse actualResult = getAllPostsUseCase.getAllPosts(new GetAllPostsRequest());

        // then
        PostInformationDTO one = PostInformationDTO.builder().id(1L).title("titleOne").content("contentOne")
                .vote(1L).userId("1").username("Shuhei").build();
        PostInformationDTO two = PostInformationDTO.builder().id(2L).title("titleTwo").content("contentTwo")
                .vote(3L).userId("1").username("Shuhei").build();
        GetAllPostsResponse expectedResult = GetAllPostsResponse.builder()
                .posts(List.of(one, two))
                        .build();

        assertEquals(expectedResult, actualResult);

        // verify
        verify(postRepository).findAll();
    }

    @Test
    void GetAll_ExistingPosts_ReturnsAllPosts_withUserId() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123456")
                .build();

        PostEntity postEntityOne = PostEntity.builder()
                .id(1L)
                .title("titleOne")
                .content("contentOne")
                .vote(1L)
                .user(userEntity)
                .build();

        PostEntity postEntityTwo = PostEntity.builder()
                .id(2L)
                .title("titleTwo")
                .content("contentTwo")
                .vote(3L)
                .user(userEntity)
                .build();

        // set up mock objects
        when(postRepository.findAllByUserId(1L)).thenReturn(List.of(postEntityOne, postEntityTwo));

        // call the method
        GetAllPostsRequest request = GetAllPostsRequest.builder()
                .userId(1L)
                .build();

        // when
        GetAllPostsResponse actualResult = getAllPostsUseCase.getAllPosts(request);

        // then
        PostInformationDTO one = PostInformationDTO.builder().id(1L).title("titleOne").content("contentOne")
                .vote(1L).userId("1").username("Shuhei").build();
        PostInformationDTO two = PostInformationDTO.builder().id(2L).title("titleTwo").content("contentTwo")
                .vote(3L).userId("1").username("Shuhei").build();
        GetAllPostsResponse expectedResult = GetAllPostsResponse.builder()
                .posts(List.of(one, two))
                .build();

        assertEquals(expectedResult, actualResult);

        // verify
        verify(postRepository).findAllByUserId(1L);
    }
}