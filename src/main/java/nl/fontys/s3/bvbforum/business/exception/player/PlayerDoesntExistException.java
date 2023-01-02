package nl.fontys.s3.bvbforum.business.exception.player;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PlayerDoesntExistException extends ResponseStatusException {
    public PlayerDoesntExistException() { super(HttpStatus.BAD_REQUEST, "PLAYER_DOESNT_EXIST"); }
}
