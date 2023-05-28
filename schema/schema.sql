-- The attributes of the zoo. Basically a key-value map.
DROP TABLE ZOO_ATTRIBUTE;
CREATE TABLE ZOO_ATTRIBUTE
(
    name  VARCHAR(100) PRIMARY KEY,
    value VARCHAR(100)
);

-- Set the initial values.
INSERT INTO ZOO_ATTRIBUTE
VALUES ('balance', '300');
INSERT INTO ZOO_ATTRIBUTE
VALUES ('currentDay', '0');

DROP TABLE HABITAT;
CREATE TABLE HABITAT
(
    id      VARCHAR(100) PRIMARY KEY,
    name    VARCHAR(100),
    climate ENUM ('TROPICAL', 'DRY', 'TEMPERATE', 'CONTINENTAL', 'POLAR'),
    used    BOOLEAN
);

DROP TABLE ANIMAL;
CREATE TABLE ANIMAL
(
    id     VARCHAR(100) PRIMARY KEY,
    name   VARCHAR(100),
    age    INTEGER,
    weight INTEGER,
    size   FLOAT,
    used   BOOLEAN
);

DROP TABLE ANIMAL_IN_HABITAT;
CREATE TABLE ANIMAL_IN_HABITAT
(
    animal_id  VARCHAR(100),
    habitat_id VARCHAR(100),
    PRIMARY KEY (animal_id, habitat_id),
    FOREIGN KEY (animal_id) REFERENCES ANIMAL (id),
    FOREIGN KEY (habitat_id) REFERENCES HABITAT (id)
);