CREATE TABLE player
(
    id          int     NOT NULL AUTO_INCREMENT,
    firstname   varchar(50)     NOT NULL,
    lastname    varchar(50)     NOT NULL,
    position    varchar(2)      NOT NULL,
    PRIMARY KEY (id)
)