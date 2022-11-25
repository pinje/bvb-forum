package nl.fontys.s3.bvbforum.business.impl.post;

import nl.fontys.s3.bvbforum.domain.Post;
import nl.fontys.s3.bvbforum.domain.PostInformationDTO;
import nl.fontys.s3.bvbforum.persistence.entity.PostEntity;

final class PostConverter {

    private PostConverter() {

    }

    public static PostInformationDTO convert(PostEntity post) {
        return PostInformationDTO.builder()
                .id(post.getId())
                .date(post.getDate())
                .title(post.getTitle())
                .content(post.getContent())
                .vote(post.getVote())
                .userId(post.getUser().getId().toString())
                .username(post.getUser().getUsername())
                .build();
    }
}
