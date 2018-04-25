CREATE SCHEMA yksidivari;

CREATE TABLE yksidivari.teos(
    teos_id     SERIAL          PRIMARY KEY,
    tyyppi      TEXT            NOT NULL,
    tekija      TEXT            NOT NULL,
    nimi        TEXT            NOT NULL,
    isbn        TEXT            NOT NULL,
    luokka      TEXT            NOT NULL,
    paino       INTEGER         NOT NULL
);

CREATE TABLE yksidivari.nide(
    nide_id             SERIAL          PRIMARY KEY,
    sisaanosto_hinta    REAL            NOT NULL,
    hinta               REAL            NOT NULL,
    teos_id             INTEGER         NOT NULL,
    divari_id           INTEGER         NOT NULL,
    FOREIGN KEY (teos_id) REFERENCES yksidivari.teos
);


