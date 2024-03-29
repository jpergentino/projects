# Base project containing the following technologies:

## Spring Boot 2.7.8 (spring-boot-starter-parent)

Created an `IndexController` as an example.

Configured with spring profiles (VM arg `-Dspring.profiles.active=dev`).

Properties files following the pattern `application-{ENV}.properties`

Logging with `logback-spring.xml` using spring profiles

## Database JPA (spring-boot-starter-data-jpa)

Configured H2 database (local file)

## Swagger UI on REST APIs (springfox-boot-starter)

Accessible on http://localhost:8080/swagger-ui/index.html

## Spring Security (spring-boot-starter-security)

Configure an InMemoryUserDetailsManager with user and admin users

Created a Form page with protected index and admin mappings

Using thymeleaf-extras-springsecurity5 dependency to use `sec:` tags on Thymeleaf

## Spring OAuth2 Security (spring-boot-starter-oauth2-client)

Configuration 

## Thymeleaf (spring-boot-starter-thymeleaf)

Created `login, index, admin and error` pages as examples

Configured static folder using ResourceHandlers

Added jQuery 3.6.3

Added Bootstrap 5.3.0

Added Bootstrap icons 1.10.3

Added PopperJS 2.11.6

# Required steps

Configure OpenID OAuth client-id on https://console.cloud.google.com/apis/credentials
