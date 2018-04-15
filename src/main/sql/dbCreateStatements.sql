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

INSERT INTO teos (isbn, tekija, nimi, vuosi,
luokka, tyyppi, myyntihinta, ostohinta, paino)
VALUES ('9155430674', 'Madeleine Brent', 'Elektran tytär', 1986, 1, 1, 10, 9, 3),
('9156381451', 'Madeleine Brent', 'Tuulentavoittelijan morsian', 1978, 1, 1, 11, 8, 4),
('0000000000', 'Mika Waltari', 'Turms kuolematon', 1995, 2, 1, 12, 7, 5),
('0000000000', 'Mika Waltari', 'Komisario Palmun erehdys', 1940, 3, 1, 13, 6, 6),
('0000000000', 'Shelton Gilbert', 'Friikkilän pojat Mexicossa', 1989, 4, 2, 14, 5, 7),
('9789510396230', 'Dale Carnegien',  'Miten saan ystäviä, menestystä, vaikutusvaltaa', 1939, 5, 3, 4, 8, 8);

INSERT INTO asiakas (ktunnus, salasana, nimi, osoite, pnumero, email)
VALUES ('jkana', 'salakala', 'Jaakko Kana', 'Pilipaliraitti 1', '0505050505', 'testimaili@mailitus.urg');
