CREATE TABLE post
(
    id         int          NOT NULL AUTO_INCREMENT,
    date       timestamp,
    title      text         NOT NULL,
    content    text         NOT NULL,
    vote       int          NOT NULL,
    user_id    int          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);