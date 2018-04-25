CREATE SCHEMA keskusdivari;

CREATE TABLE keskusdivari.teos(
    teos_id     SERIAL          PRIMARY KEY,
    tyyppi      TEXT            NOT NULL,
    tekija      TEXT            NOT NULL,
    nimi        TEXT            NOT NULL,
    isbn        TEXT            NOT NULL,
    luokka      TEXT            NOT NULL,
    paino       INTEGER         NOT NULL
);

CREATE TABLE keskusdivari.asiakas(
    asiakas_id          SERIAL          PRIMARY KEY,
    nimi                TEXT            NOT NULL,
    osoite              TEXT            NOT NULL,
    puhnro              TEXT            NOT NULL,
    email               TEXT            NOT NULL,
    ktunnus             VARCHAR(20)     NOT NULL,
    salasana            VARCHAR(20)     NOT NULL,
    admin               BOOLEAN         DEFAULT false
);

CREATE TABLE keskusdivari.divari(
    divari_id           SERIAL          PRIMARY KEY,
    nimi                TEXT            NOT NULL,
    osoite              TEXT            NOT NULL,
    webosoite           TEXT            NOT NULL,
    admin_id            INTEGER         NOT NULL,
    FOREIGN KEY (admin_id) REFERENCES keskusdivari.asiakas (asiakas_id)
);

CREATE TABLE keskusdivari.nide(
    nide_id             SERIAL,
    sisaanosto_hinta    REAL            NOT NULL,
    hinta               REAL            NOT NULL,
    teos_id             INTEGER         NOT NULL,
    divari_id           INTEGER         NOT NULL,
    PRIMARY KEY (nide_id),
    FOREIGN KEY (teos_id) REFERENCES keskusdivari.teos (teos_id),
    FOREIGN KEY (divari_id) REFERENCES keskusdivari.divari (divari_id)
);

CREATE TABLE keskusdivari.tilaus(
    tilaus_id           SERIAL          PRIMARY KEY,
    pvm                 DATE            NOT NULL,
    tila                TEXT            NOT NULL,
    asiakas_id          INTEGER         NOT NULL,
    nide_id             INTEGER         NOT NULL,
    FOREIGN KEY (asiakas_id) REFERENCES keskusdivari.asiakas (asiakas_id),
    FOREIGN KEY (nide_id) REFERENCES keskusdivari.nide (nide_id)
);
