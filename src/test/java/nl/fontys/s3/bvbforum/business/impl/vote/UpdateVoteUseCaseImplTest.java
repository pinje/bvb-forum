package nl.fontys.s3.bvbforum.business.impl.vote;

import nl.fontys.s3.bvbforum.domain.request.vote.UpdateVoteRequest;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateVoteUseCaseImplTest {
    @Mock
    private VoteRepository voteRepository;
    @InjectMocks
    private UpdateVoteUseCaseImpl updateVoteUseCase;

    @Test
    void Update_Vote_FromUpToDown() {
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
        when(voteRepository.findById(1L)).thenReturn(Optional.of(voteEntityOne));
        when(voteRepository.save(voteEntityOne)).thenReturn(voteEntityOne);

        // call the method
        UpdateVoteRequest request = UpdateVoteRequest.builder()
                .user(111L)
                .post(222L)
                .build();

        // when
        updateVoteUseCase.updateVote(request);

        // then
        assertEquals(false, voteEntityOne.getType());

        // verify
        verify(voteRepository, times(1)).findAllByPostId(222L);
        verify(voteRepository, times(1)).findById(1L);
        verify(voteRepository, times(1)).save(voteEntityOne);
    }

    @Test
    void Update_Vote_FromDownToUp() {
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
        when(voteRepository.findById(2L)).thenReturn(Optional.of(voteEntityTwo));
        when(voteRepository.save(voteEntityTwo)).thenReturn(voteEntityTwo);

        // call the method
        UpdateVoteRequest request = UpdateVoteRequest.builder()
                .user(777L)
                .post(222L)
                .build();

        // when
        updateVoteUseCase.updateVote(request);

        // then
        assertEquals(true, voteEntityTwo.getType());

        // verify
        verify(voteRepository, times(1)).findAllByPostId(222L);
        verify(voteRepository, times(1)).findById(2L);
        verify(voteRepository, times(1)).save(voteEntityTwo);
    }
}