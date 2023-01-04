CREATE TABLE rating_post
(
    id          int         NOT NULL AUTO_INCREMENT,
    date        timestamp   NOT NULL,
    start_year  int         NOT NULL,
    end_year    int         NOT NULL,
    matchday    int         NOT NULL,
    opponent    varchar(50) NOT NULL,
    tournament  varchar(20) NOT NULL,
    PRIMARY KEY (id)
)