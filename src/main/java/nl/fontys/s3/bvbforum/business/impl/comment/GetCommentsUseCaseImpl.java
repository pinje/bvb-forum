package nl.fontys.s3.bvbforum.business.impl.comment;

import lombok.AllArgsConstructor;
import nl.fontys.s3.bvbforum.business.interfaces.comment.GetCommentsUseCase;
import nl.fontys.s3.bvbforum.domain.CommentInformationDTO;
import nl.fontys.s3.bvbforum.domain.request.comment.GetCommentsRequest;
import nl.fontys.s3.bvbforum.domain.response.comment.GetCommentsResponse;
import nl.fontys.s3.bvbforum.persistence.CommentRepository;
import nl.fontys.s3.bvbforum.persistence.entity.CommentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetCommentsUseCaseImpl implements GetCommentsUseCase {
    private CommentRepository commentRepository;

    @Override
    public GetCommentsResponse getCommentsByPostId(final GetCommentsRequest request) {
        List<CommentEntity> results;
        results = commentRepository.findAllByPostId(request.getId());

        final GetCommentsResponse response = new GetCommentsResponse();

        List<CommentInformationDTO> comments = results
                .stream()
                .map(CommentConverter::convert)
                .toList();

        response.setComments(comments);

        return response;
    }

    @Override
    public GetCommentsResponse getCommentsByUserId(final GetCommentsRequest request) {
        List<CommentEntity> results;
        results = commentRepository.findAllByUserId(request.getId());

        final GetCommentsResponse response = new GetCommentsResponse();

        List<CommentInformationDTO> comments = results
                .stream()
                .map(CommentConverter::convert)
                .toList();

        response.setComments(comments);

        return response;
    }
}
