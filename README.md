# Setup

* Docker
* Docker Compose
* JDK >= 11

# Enable migration tool (flyway or liquibase)

* Change `spring.flyway.enabled=false` or `spring.liquibase.enabled=false` to `true`

# How to run

* Start backend services in `docker` folder
    * `cd docker; docker-compose up -d;`
* Start `fruitshop` with gradle wrapper
    * `./gradlew bootRun` (Linux)
    * `gradlew.bat bootRun` (Windows)

# How to use

All controllers export there functionality via http.
You must be authenticated with a valid user (check first steps).

* Use `curl` or `httpie` (Linux)
* Use `postman` (Any OS)

## httpie Examples

The port is 8080, this can be defined if wished.
Authentication with session needs to be done once.

* GET call: `http --session=SESSION_NAME -a USER:PASSWORD IP:8080/fruits/`
* POST call: `http --session=SESSION_NAME POST IP:8080/fruits/ < FRUIT.json`
* DELETE call: `http --session=SESSION_NAME DELETE IP:8080/fruits/`

S3:

* GET call: `http --session=SESSION_NAME GET :8080/s3/ key==FILE.PDF`
* GET call (Browser): `http://localhost:8080/s3/?key=FILE.pdf`
* POST call: `http -f --session=SESSION_NAME POST :8080/s3/ key==FILE.PDF file@'PATH_TO_FILE.pdf;type=application/pdf'`

# Docker Fruitshop

If you want to run the `fruitshop` as a docker image.

* Build jar
    * `./gradlew bootJar`
* Build docker image (use the provided Dockerfile)
    * `docker build . -t fruitshop:latest`
* Run the docker image in correct network
    * `docker run -it --rm --name fruitshop \
      --network docker_fruitshop \
      -p 8080:8080 \
      -e DATASOURCE=postgres \
      -e REDIS=redis \
      fruitshop`
