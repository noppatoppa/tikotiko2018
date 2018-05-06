INSERT INTO keskusdivari.asiakas (nimi, osoite, puhnro, email, ktunnus, salasana, admin)
VALUES ('jaska', 'fsdfads',	'fdsfdsa', 'fdfsad', 'jaska', 'foo', false),
('admin1', 'osote', 'nro', 'email', 'admin1', 'foo', true),
('admin2', 'osote', 'nro', 'email', 'admin2', 'foo', true);

INSERT INTO keskusdivari.divari (nimi, osoite, webosoite, admin_id)
VALUES ('Lassen lehti', 'Lassentie 1', 'http://www.lassenlehti.foo', 2);

INSERT INTO keskusdivari.divari (nimi, osoite, webosoite, admin_id)
VALUES ('Galleinn Galle', 'Gallenpolku 1', 'http://www.gallendivari.foo', 3);

INSERT INTO yksidivari.teos (isbn, tekija, nimi,
luokka, tyyppi, paino)
VALUES ('9155430674', 'Madeleine Brent', 'Elektran tytär', 'romantiikka', 'romaani', 300),
('9156381451', 'Madeleine Brent', 'Tuulentavoittelijan morsian', 'romantiikka', 'romaani', 300),
('No ISBN available', 'Mika Waltari', 'Turms kuolematon', 'historia', 'romaani', 500),
('No ISBN available', 'Mika Waltari', 'Komisario Palmun erehdys', 'dekkari', 'romaani', 600),
('No ISBN available', 'Shelton Gilbert', 'Friikkilän pojat Mexicossa', 'huumori', 'sarjakuva', 700),
('9789510396230', 'Dale Carnegien',  'Miten saan ystäviä, menestystä, vaikutusvaltaa', 'opas', 'tietokirja', 800);

INSERT INTO yksidivari.nide (teos_id, divari_id, sisaanosto_hinta, hinta)
VALUES (1, 1, 10, 20),
(2, 1, 15, 20),
(3, 1, 12, 13),
(4, 1, 14, 16),
(5, 1, 9, 10),
(6, 1, 4, 5);

INSERT INTO keskusdivari.teos (isbn, tekija, nimi,
luokka, tyyppi, paino)
VALUES ('9155430674', 'Madeleine Brent', 'Elektran tytär', 'romantiikka', 'romaani', 300),
('9156381451', 'Madeleine Brent', 'Tuulentavoittelijan morsian', 'romantiikka', 'romaani', 300),
('No ISBN available', 'Mika Waltari', 'Turms kuolematon', 'historia', 'romaani', 500),
('No ISBN available', 'Mika Waltari', 'Komisario Palmun erehdys', 'dekkari', 'romaani', 600),
('No ISBN available', 'Shelton Gilbert', 'Friikkilän pojat Mexicossa', 'huumori', 'sarjakuva', 700),
('9789510396230', 'Dale Carnegien',  'Miten saan ystäviä, menestystä, vaikutusvaltaa', 'opas', 'tietokirja', 800);

INSERT INTO keskusdivari.nide (teos_id, divari_id, sisaanosto_hinta, hinta)
VALUES (1, 1, 10, 20),
(2, 1, 15, 20),
(3, 1, 12, 13),
(4, 1, 14, 16),
(5, 1, 9, 10),
(6, 1, 4, 5),
(2, 2, 16, 25),
(3, 2, 12, 16),
(4, 2, 14, 19),
(5, 2, 9, 18),
(6, 2, 4, 15);