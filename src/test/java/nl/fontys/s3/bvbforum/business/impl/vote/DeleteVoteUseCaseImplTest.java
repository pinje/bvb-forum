package nl.fontys.s3.bvbforum.business.impl.vote;

import nl.fontys.s3.bvbforum.domain.request.vote.DeleteVoteRequest;
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
class DeleteVoteUseCaseImplTest {
    @Mock
    private VoteRepository voteRepository;
    @InjectMocks
    private DeleteVoteUseCaseImpl deleteVoteUseCase;

    @Test
    void Delete_ExistingVote_Success() {
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

        // set up mock objects
        when(voteRepository.findAllByPostId(222L)).thenReturn(List.of(voteEntityOne, voteEntityTwo));

        // call the method
        DeleteVoteRequest request = DeleteVoteRequest.builder()
                .user(111L)
                .post(222L)
                .build();
        // when
        deleteVoteUseCase.deleteVote(request);

        // verify
        verify(voteRepository, times(1)).findAllByPostId(222L);
    }
}