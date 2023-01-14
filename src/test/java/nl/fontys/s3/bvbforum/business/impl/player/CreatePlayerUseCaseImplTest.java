package nl.fontys.s3.bvbforum.business.impl.player;

import nl.fontys.s3.bvbforum.domain.request.player.CreatePlayerRequest;
import nl.fontys.s3.bvbforum.domain.response.player.CreatePlayerResponse;
import nl.fontys.s3.bvbforum.persistence.PlayerRepository;
import nl.fontys.s3.bvbforum.persistence.entity.PlayerEntity;
import nl.fontys.s3.bvbforum.persistence.entity.PositionEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePlayerUseCaseImplTest {
    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private CreatePlayerUseCaseImpl createPlayerUseCase;

    @Test
    void Add_ValidPlayer_PlayerSavedInRepository() {
        // given
        PlayerEntity playerEntity = PlayerEntity.builder()
                .id(1L)
                .firstname("marco")
                .lastname("reus")
                .position(PositionEnum.MF)
                .build();

        PlayerEntity requestPlayer = PlayerEntity.builder()
                .firstname("marco")
                .lastname("reus")
                .position(PositionEnum.MF)
                .build();

        // set up mock objects
        when(playerRepository.save(requestPlayer)).thenReturn(playerEntity);

        // call the method
        CreatePlayerRequest request = CreatePlayerRequest.builder()
                .firstname("marco")
                .lastname("reus")
                .position(PositionEnum.valueOf("MF"))
                .build();

        // when
        CreatePlayerResponse response = createPlayerUseCase.createPlayer(request);

        // then
        assertNotNull(response.getPlayerId());

        // verify
        verify(playerRepository, times(1)).save(requestPlayer);
    }
}