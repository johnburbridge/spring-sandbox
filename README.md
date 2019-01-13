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
### Running
```
docker-compose up -d
```
You can access the API here:
http://dev.metabuild.org:8080/swagger-ui.html

And the front-end here:
http://dev.metabuild.org:8081/home

```
Username: user@metabuild.org
Password: test
```

