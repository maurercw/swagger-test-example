# swagger-test-example
## Running the web app

### Run with swagger stuff disabled

```mvn spring-boot:run -Dspring-boot.run.profiles=swagger-disabled```

No swagger endpoints or ui available at default locations.

http://localhost:8080/api-docs

http://localhost:8080/swagger-ui.html

### Run with swagger defaults enabled

```mvn spring-boot:run -Dspring-boot.run.profiles=swagger-default```

Swagger stuff should be accessible at the default locations:

http://localhost:8080/api-docs

http://localhost:8080/swagger-ui.html

### Run with swagger customized paths enabled

```mvn spring-boot:run -Dspring-boot.run.profiles=swagger-custom```

Swagger stuff should be accessible at the customized locations:

http://localhost:8080/custom/api-docs

http://localhost:8080/custom/swagger-ui.html

## Running tests
`mvn test`

When things are in the current "broken" state, there will be 6 test failures that I believe are wrong.
When everything is enabled with the defaults, everything looks good.
When everything is disabled, default paths still seem to be available.
When everything is enabled with custom paths set, things are still available at the default location instead of the custom location.

So, it seems that unit tests don't respect the same settings as the web application.