Spring Sandbox
--------------
John's experiments with Spring Boot.

## Development Setup

### /etc/hosts
You must setup an alias for _dev.metabuild.org_ mapping to _localhost_. I.e:

`127.0.0.1 localhost dev.metabuild.org`

### Running
You can run the API server by issuing:
```
gradle api:bootRun
```

And access it by going to:
http://dev.metabuild.org:8080/swagger-ui.html

```
Username: user@metabuild.org
Password: test
```
