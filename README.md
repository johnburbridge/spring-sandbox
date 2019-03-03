Spring Sandbox
--------------
John's experiments with Spring Boot.

## Development Setup

### /etc/hosts
You must setup an alias for _dev.metabuild.org_ mapping to _localhost_. I.e:

`127.0.0.1 localhost dev.metabuild.org`

### Building
```
./gradlew clean test bootJar docker
```
### Running a minimal stack
Using docker-compose for a single node for each service:
```
docker-compose up -d
```
You can access the API here:
http://dev.metabuild.org:8080/swagger-ui.html

And the front-end here:
http://dev.metabuild.org:8081/home

```
Username: test@metabuild.org
Password: test
```

And the admin page here:
http://dev.metabuild.org:8081/admin

```
Username: admin@metabuild.org
Password: Passw0rd
```
### Running a better stack
Using docker swarm for multiple frontends and HAProxy:
```
docker swarm init
docker stack deploy --compose-file=docker-swarm.yml dev
```

In addition to the services above, now you can also access HAProxy on port 80:
http://dev.metabuild.org
