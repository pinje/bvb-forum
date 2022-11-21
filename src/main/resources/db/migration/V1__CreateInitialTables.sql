CREATE TABLE user
(
    id  int  NOT NULL    AUTO_INCREMENT,
    username    varchar(50) NOT NULL,
    password    varchar(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (username)
);

CREATE TABLE user_role
(
    id        int         NOT NULL AUTO_INCREMENT,
    user_id   int         NOT NULL,
    role_name varchar(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES user (id)
);