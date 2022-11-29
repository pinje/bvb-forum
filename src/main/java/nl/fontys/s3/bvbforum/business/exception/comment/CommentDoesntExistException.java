package nl.fontys.s3.bvbforum.business.exception.comment;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CommentDoesntExistException extends ResponseStatusException {
    public CommentDoesntExistException() { super(HttpStatus.BAD_REQUEST, "COMMENT_DOESNT_EXIST"); }
}
