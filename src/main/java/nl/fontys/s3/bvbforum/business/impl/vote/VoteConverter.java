package nl.fontys.s3.bvbforum.business.impl.vote;

import nl.fontys.s3.bvbforum.domain.VoteInformationDTO;
import nl.fontys.s3.bvbforum.persistence.entity.VoteEntity;

final class VoteConverter {

    private VoteConverter() {

    }

    public static VoteInformationDTO convert(VoteEntity vote) {
        return VoteInformationDTO.builder()
                .id(vote.getId())
                .type(vote.getType())
                .userId(vote.getUser().getId())
                .postId(vote.getPost().getId())
                .build();
    }
}
