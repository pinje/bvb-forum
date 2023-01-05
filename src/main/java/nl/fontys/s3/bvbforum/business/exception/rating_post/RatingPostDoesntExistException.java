package nl.fontys.s3.bvbforum.business.exception.rating_post;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RatingPostDoesntExistException extends ResponseStatusException {
    public RatingPostDoesntExistException() { super(HttpStatus.BAD_REQUEST, "RATING_POST_DOESNT_EXIST"); }
}
