# To start the backend application locally:

#### Required Technologies
This project requires that you have:
- JDK 9 or higher
- docker-compose

## Steps:
note: All commands given in the steps below should be run from the project root directory
#### First
build the project with `gradlew clean build idea`

#### Second
start the database layer with `docker-compose up`

#### Third
run the backend application with `java -jar sKeduler-backend/build/libs/sKeduler-1.0.SNAPSHOT.jar`


#### Fourth
That's it! Your Skeduler backend is now up and running- try making some requests to it via Postman or CURL.
