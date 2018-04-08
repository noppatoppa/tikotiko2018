-- Kannan luontilauseet

CREATE TABLE divari(
	divari_id		SERIAL		PRIMARY KEY,
	nimi			TEXT			NOT NULL,
	osoite		TEXT			NOT NULL,
	websivu		TEXT
);

CREATE TABLE asiakas(
	asiakas_id		SERIAL 		PRIMARY KEY,
	ktunnus		TEXT			UNIQUE NOT NULL,
	salasana		TEXT			NOT NULL,
	nimi			TEXT	 		NOT NULL,
	osoite		TEXT 			NOT NULL,
	pnumero		TEXT			NOT NULL,
	email			TEXT			NOT NULL
);

CREATE TABLE teos(
	teos_id		SERIAL		PRIMARY KEY,
	isbn			TEXT			NOT NULL,
	tekija		TEXT			NOT NULL,
	nimi			TEXT			NOT NULL,
	vuosi			INTEGER		NOT NULL,
	luokka		INTEGER		NOT NULL,
	tyyppi		INTEGER		NOT NULL,
	myyntihinta		REAL			NOT NULL,
	ostohinta		REAL			NOT NULL,
	myyntipvm		DATE,
	paino			INTEGER		NOT NULL
);

CREATE TABLE tilaus(
	tilaus_id		SERIAL		PRIMARY KEY,
	tilausnro		INTEGER		NOT NULL,
	asiakas_id		INTEGER	 	NOT NULL,
	teos_id		INTEGER 		NOT NULL,
	FOREIGN KEY (asiakas_id) REFERENCES asiakas (asiakas_id),
	FOREIGN KEY (teos_id)	 REFERENCES teos	(teos_id)
);

INSERT INTO teos (isbn, tekija, nimi, vuosi, luokka, tyyppi, myyntihinta, ostohinta, paino)
VALUES ('9155430674', 'Madeleine Brent', 'Elektran tytär', 1986, 1, 1, 10, 9, 3);