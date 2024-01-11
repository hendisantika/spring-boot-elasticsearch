INSERT INTO authors (name)
VALUES ('Sasuke'),
       ('Yuji');

INSERT INTO tags (name)
VALUES ('Technology'),
       ('Social');

INSERT INTO posts (title, body, author_id)
VALUES ('My First Blog', 'bla bla bla', 1),
       ('Opening Party!', 'bla bla bla', 2);

INSERT INTO post_tags (post_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (2, 1);
