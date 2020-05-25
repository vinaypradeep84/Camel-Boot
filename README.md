# camel-assignment

This project demonstrates Apache Camel and Spring Boot integration.
It exposes POST and GET restful endpoints.
Persists POST requests to in-memory database.
Project builds a docker image to deploy on Kubernetes cluster.


To run the project use below commands.
1. mvn clean install
2. mvn spring-boot:run

To access the endpoints use below URLs.

GET http://localhost:8080/camel/users

POST http://localhost:8080/camel/users
body:
{
	"name": "Name of the User"
}
