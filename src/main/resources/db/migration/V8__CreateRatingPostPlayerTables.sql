CREATE TABLE rating_post_player
(
    id          int     NOT NULL AUTO_INCREMENT,
    player_id   int     NOT NULL,
    rating_post_id  int     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (player_id) REFERENCES player (id),
    FOREIGN KEY (rating_post_id) REFERENCES rating_post (id)
)