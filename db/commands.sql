CREATE TABLE person (
    id INTEGER NOT NULL PRIMARY KEY,
    name TEXT,
    birthday DATE
    );

DROP TABLE person;

INSERT INTO person (id, name, birthday) values (1, "Hugo Opa", "1900-05-06");

SELECT * FROM person;

DELETE FROM person WHERE id = 1;

UPDATE person SET name = "valaki" WHERE id = 1;




