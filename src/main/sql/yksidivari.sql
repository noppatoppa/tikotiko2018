CREATE SCHEMA yksidivari;

CREATE TABLE yksidivari.asiakas(
    asiakas_id          SERIAL          PRIMARY KEY,
    nimi                TEXT            NOT NULL,
    osoite              TEXT            NOT NULL,
    puhnro              TEXT            NOT NULL,
    email               TEXT            NOT NULL
);

CREATE TABLE yksidivari.teos(
    teos_id     SERIAL          PRIMARY KEY,
    tyyppi      TEXT            NOT NULL,
    tekija      TEXT            NOT NULL,
    nimi        TEXT            NOT NULL,
    isbn        TEXT            NOT NULL,
    luokka      TEXT            NOT NULL,
    paino       INTEGER         NOT NULL
);

CREATE TABLE yksidivari.divari(
    divari_id           SERIAL          PRIMARY KEY,
    nimi                TEXT            NOT NULL,
    osoite              TEXT            NOT NULL,
    webosoite           TEXT            NOT NULL
);

CREATE TABLE yksidivari.nide(
    nide_id             SERIAL          PRIMARY KEY,
    sisaanosto_hinta    REAL            NOT NULL,
    hinta               REAL            NOT NULL,
    teos_id             INTEGER         NOT NULL,
    divari_id           INTEGER         NOT NULL,
    FOREIGN KEY (teos_id) REFERENCES yksidivari.teos,
    FOREIGN KEY (divari_id) REFERENCES yksidivari.divari
);

CREATE TABLE yksidivari.tilaus(
    tilaus_id           SERIAL          PRIMARY KEY,
    pvm                 DATE            NOT NULL,
    tila                TEXT            NOT NULL,
    asiakas_id          INTEGER         NOT NULL,
    nide_id             INTEGER         NOT NULL,
    FOREIGN KEY (asiakas_id) REFERENCES yksidivari.asiakas,
    FOREIGN KEY (nide_id) REFERENCES yksidivari.nide
);

CREATE TABLE yksidivari.asiakastiedot(
    asiakas_id          INTEGER,
    divari_id           INTEGER,
    PRIMARY KEY (asiakas_id, divari_id),
    FOREIGN KEY (asiakas_id) REFERENCES yksidivari.asiakas,
    FOREIGN KEY (divari_id) REFERENCES yksidivari.divari
);