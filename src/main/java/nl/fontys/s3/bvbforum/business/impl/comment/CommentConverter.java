package nl.fontys.s3.bvbforum.business.impl.comment;

import nl.fontys.s3.bvbforum.domain.CommentInformationDTO;
import nl.fontys.s3.bvbforum.persistence.entity.CommentEntity;

final class CommentConverter {

    private CommentConverter() {

    }

    public static CommentInformationDTO convert(CommentEntity comment) {
        return CommentInformationDTO.builder()
                .id(comment.getId())
                .date(comment.getDate())
                .comment(comment.getComment())
                .userId(comment.getUser().getId().toString())
                .username(comment.getUser().getUsername())
                .build();
    }
}
