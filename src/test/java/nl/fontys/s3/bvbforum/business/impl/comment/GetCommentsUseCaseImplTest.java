package nl.fontys.s3.bvbforum.business.impl.comment;

import nl.fontys.s3.bvbforum.domain.CommentInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.comment.GetCommentsRequest;
import nl.fontys.s3.bvbforum.domain.response.comment.GetCommentsResponse;
import nl.fontys.s3.bvbforum.persistence.CommentRepository;
import nl.fontys.s3.bvbforum.persistence.entity.CommentEntity;
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
public class GetCommentsUseCaseImplTest {
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private GetCommentsUseCaseImpl getCommentsUseCase;

    @Test
    void GetAll_ExistingComments_ReturnsAllComments_withPostId() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123456")
                .build();

        PostEntity postEntity = PostEntity.builder()
                .id(222L)
                .title("title")
                .content("content")
                .vote(1L)
                .user(userEntity)
                .build();

        CommentEntity commentEntityOne = CommentEntity.builder()
                .id(1L)
                .comment("commentOne")
                .user(userEntity)
                .post(postEntity)
                .build();

        CommentEntity commentEntityTwo = CommentEntity.builder()
                .id(2L)
                .comment("commentTwo")
                .user(userEntity)
                .post(postEntity)
                .build();

        // set up mock objects
        when(commentRepository.findAllByPostId(222L)).thenReturn(List.of(commentEntityOne, commentEntityTwo));

        // call the method
        GetCommentsRequest request = GetCommentsRequest.builder()
                .id(222L)
                .build();

        // when
        GetCommentsResponse actualResult = getCommentsUseCase.getCommentsByPostId(request);

        // then
        CommentInformationDTO one = CommentInformationDTO.builder().id(1L).comment("commentOne").userId("1")
                .username("Shuhei").build();
        CommentInformationDTO two = CommentInformationDTO.builder().id(2L).comment("commentTwo").userId("1")
                .username("Shuhei").build();
        GetCommentsResponse expectedResult = GetCommentsResponse.builder()
                .comments(List.of(one, two)).build();

        assertEquals(expectedResult, actualResult);

        // verify
        verify(commentRepository).findAllByPostId(222L);
    }

    @Test
    void GetAll_ExistingComments_ReturnsAllComments_withUserId() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(444L)
                .username("Shuhei")
                .password("123456")
                .build();

        PostEntity postEntity = PostEntity.builder()
                .id(222L)
                .title("title")
                .content("content")
                .vote(1L)
                .user(userEntity)
                .build();

        CommentEntity commentEntityOne = CommentEntity.builder()
                .id(1L)
                .comment("commentOne")
                .user(userEntity)
                .post(postEntity)
                .build();

        CommentEntity commentEntityTwo = CommentEntity.builder()
                .id(2L)
                .comment("commentTwo")
                .user(userEntity)
                .post(postEntity)
                .build();

        // set up mock objects
        when(commentRepository.findAllByUserId(444L)).thenReturn(List.of(commentEntityOne, commentEntityTwo));

        // call the method
        GetCommentsRequest request = GetCommentsRequest.builder()
                .id(444L)
                .build();

        // when
        GetCommentsResponse actualResult = getCommentsUseCase.getCommentsByUserId(request);

        // then
        CommentInformationDTO one = CommentInformationDTO.builder().id(1L).comment("commentOne").userId("444")
                .username("Shuhei").build();
        CommentInformationDTO two = CommentInformationDTO.builder().id(2L).comment("commentTwo").userId("444")
                .username("Shuhei").build();
        GetCommentsResponse expectedResult = GetCommentsResponse.builder()
                .comments(List.of(one, two)).build();

        assertEquals(expectedResult, actualResult);

        // verify
        verify(commentRepository).findAllByUserId(444L);
    }
}
