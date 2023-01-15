package nl.fontys.s3.bvbforum.business.impl.vote;

import nl.fontys.s3.bvbforum.domain.VoteInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.vote.GetVoteRequest;
import nl.fontys.s3.bvbforum.persistence.VoteRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import nl.fontys.s3.bvbforum.persistence.entity.VoteEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetVoteUseCaseImplTest {
    @Mock
    private VoteRepository voteRepository;
    @InjectMocks
    private GetVoteUseCaseImpl getVoteUseCase;

    @Test
    void Get_VoteByPostIdAndUserId_ReturnsVoteDTO() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(111L)
                .username("Shuhei")
                .password("123456")
                .build();

        UserEntity userEntityTwo = UserEntity.builder()
                .id(777L)
                .username("Fontys")
                .password("123456")
                .build();

        PostEntity postEntity = PostEntity.builder()
                .id(222L)
                .title("title")
                .content("content")
                .user(userEntity)
                .build();

        VoteEntity voteEntityOne = VoteEntity.builder()
                .id(1L)
                .type(true)
                .user(userEntity)
                .post(postEntity)
                .build();

        VoteEntity voteEntityTwo = VoteEntity.builder()
                .id(2L)
                .type(false)
                .user(userEntityTwo)
                .post(postEntity)
                .build();

        VoteInformationDTO expectedResult = VoteInformationDTO.builder()
                .id(1L)
                .type(true)
                .userId(111L)
                .postId(222L)
                .build();
        
        // set up mock objects
        when(voteRepository.findAllByPostId(222L)).thenReturn(List.of(voteEntityOne, voteEntityTwo));

        // call the method
        GetVoteRequest request = GetVoteRequest.builder()
                .post(222L)
                .user(111L)
                .build();

        // when
        VoteInformationDTO actualVote = getVoteUseCase.getVote(request);

        // then
        assertNotNull(actualVote);
        assertEquals(expectedResult, actualVote);

        // verify
        verify(voteRepository, times(1)).findAllByPostId(222L);
    }
}