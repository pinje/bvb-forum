package nl.fontys.s3.bvbforum.business.interfaces.login;

import nl.fontys.s3.bvbforum.domain.AccessToken;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
