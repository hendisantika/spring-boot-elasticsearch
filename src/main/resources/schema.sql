DROP TABLE if EXISTS post_tags;
DROP TABLE if EXISTS posts;
DROP TABLE if EXISTS authors;
DROP TABLE if EXISTS tags;

CREATE TABLE authors
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(250) NOT NULL,
    created_on      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tags
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(250) NOT NULL,
    created_on      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE posts
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(250) NOT NULL,
    body            VARCHAR(250) NOT NULL,
    author_id       INT,
    created_on      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES authors (id)
);

CREATE TABLE post_tags
(
    post_id INT,
    tag_id  INT,
    PRIMARY KEY (post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES posts (id),
    FOREIGN KEY (tag_id) REFERENCES tags (id)
);
