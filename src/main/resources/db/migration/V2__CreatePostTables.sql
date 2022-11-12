CREATE TABLE post
(
    id         int          NOT NULL AUTO_INCREMENT,
    date       timestamp         NOT NULL,
    title      varchar(50)  NOT NULL,
    content    varchar(500) NOT NULL,
    vote       int          NOT NULL,
    user_id    int          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);