CREATE TABLE rating
(
    id          int     NOT NULL AUTO_INCREMENT,
    player_id   int     NOT NULL,
    rating      int     NOT NULL,
    user_id     int     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (player_id) REFERENCES player (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
)