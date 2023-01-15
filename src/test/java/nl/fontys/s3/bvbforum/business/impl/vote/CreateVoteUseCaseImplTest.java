package nl.fontys.s3.bvbforum.business.impl.vote;

import nl.fontys.s3.bvbforum.domain.request.vote.CreateVoteRequest;
import nl.fontys.s3.bvbforum.domain.response.vote.CreateVoteResponse;
import nl.fontys.s3.bvbforum.persistence.PostRepository;
import nl.fontys.s3.bvbforum.persistence.UserRepository;
import nl.fontys.s3.bvbforum.persistence.VoteRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;
import nl.fontys.s3.bvbforum.persistence.entity.UserEntity;
import nl.fontys.s3.bvbforum.persistence.entity.VoteEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateVoteUseCaseImplTest {
    @Mock
    private VoteRepository voteRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private CreateVoteUseCaseImpl createVoteUseCase;

    @Test
    void Add_ValidVote_VoteSavedInRepository() {
        // given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("Shuhei")
                .password("123456")
                .build();

        PostEntity postEntity = PostEntity.builder()
                .id(11L)
                .title("title")
                .content("content")
                .vote(1L)
                .user(userEntity)
                .build();

        VoteEntity voteEntity = VoteEntity.builder()
                .id(444L)
                .type(true)
                .user(userEntity)
                .post(postEntity)
                .build();

        VoteEntity requestVoteEntity = VoteEntity.builder()
                .type(true)
                .user(userEntity)
                .post(postEntity)
                .build();

        // set up mock objects
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(userEntity));
        when(postRepository.findById(11L)).thenReturn(Optional.ofNullable(postEntity));
        when(voteRepository.save(requestVoteEntity)).thenReturn(voteEntity);

        // call the method
        CreateVoteRequest request = CreateVoteRequest.builder()
                .type(true)
                .user(1L)
                .post(11L)
                .build();

        // when
        CreateVoteResponse response = createVoteUseCase.createVote(request);

        // then
        assertEquals(444L, response.getVoteId());

        // verify
        verify(userRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).findById(11L);
    }
}