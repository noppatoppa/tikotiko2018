CREATE SCHEMA divari;

CREATE TABLE asiakas(
    asiakas_id          SERIAL          PRIMARY KEY,
    nimi                TEXT            NOT NULL,
    osoite              TEXT            NOT NULL,
    puhnro              TEXT            NOT NULL,
    email               TEXT            NOT NULL,
);

CREATE TABLE teos(
    teos_id     SERIAL          PRIMARY KEY,
    tyyppi      TEXT            NOT NULL,
    tekija      TEXT            NOT NULL,
    nimi        TEXT            NOT NULL,
    isbn        TEXT            NOT NULL,
    luokka      TEXT            NOT NULL,
    paino       INTEGER         NOT NULL,
);

CREATE TABLE nide(
    nide_id             SERIAL          PRIMARY KEY,
    sisaanosto_hinta    REAL            NOT NULL,
    hinta               REAL            NOT NULL
    teos_id             INTEGER         NOT NULL,
    divari_id           INTEGER         NOT NULL,
    FOREIGN KEY teos_id REFERENCES teos,
    FOREIGN KEY divari_id REFERENCES divari
);

CREATE TABLE tilaus(
    tilaus_id           SERIAL          PRIMARY KEY,
    pvm                 DATE            NOT NULL,
    tila                TEXT            NOT NULL,
    asiakas_id          INTEGER         NOT NULL,
    nide_id             INTEGER         NOT NULL,
    FOREIGN KEY asiakas_id REFERENCES asiakas
    FOREIGN KEY nide_id REFERENCES nide
);

CREATE TABLE divari(
    divari_id           SERIAL          PRIMARY KEY,
    nimi                TEXT            NOT NULL,
    osoite              TEXT            NOT NULL,
    webosoite           TEXT            NOT NULL,
);

CREATE TABLE asiakastiedot(
    asiakas_id          INTEGER         PRIMARY KEY,
    divari_id           INTEGER         PRIMARY KEY,
    FOREIGN KEY asiakas_id REFERENCES asiakas,
    FOREIGN KEY divari_id REFERENCES divari
);