CREATE TABLE vote
(
    id          int         NOT NULL AUTO_INCREMENT,
    type        boolean     NOT NULL,
    user_id     int         NOT NULL,
    post_id     int         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE
)