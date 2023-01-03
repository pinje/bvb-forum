package nl.fontys.s3.bvbforum.business.exception.rating;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RatingDoesntExistException extends ResponseStatusException {
    public RatingDoesntExistException() { super(HttpStatus.BAD_REQUEST, "RATING_DOESNT_EXIST"); }
}
