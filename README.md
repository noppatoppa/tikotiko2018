# tikotiko2018
Tiko harjoitustyön repo

Testicommit

Käynnistä docker:
docker run --name divari -e POSTGRES_PASSWORD=salakala -d postgres

Yhdistä psql:llä:
docker run -it --rm --link divari:postgres postgres psql -h postgres -U postgres

