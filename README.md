# Setup

* Docker
* Docker Compose
* JDK >= 11

# How to run

* Start backend services in `docker` folder
    * `cd docker; docker-compose up -d;`
* Start `auctionhouse` with gradle wrapper
    * `./gradlew bootRun` (Linux)
    * `gradlew.bat bootRun` (Windows)

# How to use

All controllers export there functionality via http.
You must be authenticated with a valid user (check first steps).

* Use `postman` (Postman export file is in `postman` folder)
* There is one User when you first start the application. (username: `noahz` password: `test`)
* More users can be created using this user.

## Postman Examples

All Examples needed to use the application are in the Postman file. Import it in to Postman and you can use it.