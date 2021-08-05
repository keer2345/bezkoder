# [Spring Boot JWT Authentication example with Spring Security & Spring Data JPA](https://www.bezkoder.com/spring-boot-jwt-authentication)

> [Source Code](https://github.com/bezkoder/spring-boot-spring-security-jwt-authentication)

## Overview

### User Registration, User Login and Authorization process.

The diagram shows flow of how we implement User Registration, User Login and Authorization process.

![](imgs/img.png)

### Spring Boot Server Architecture with Spring Security

You can have an overview of our Spring Boot Server with the diagram below:

![](imgs/img_1.png)

### Refresh Token

![](imgs/img_2.png)

## Technology

- Java 8
- Spring Boot 2.5.3 (with Spring Security, Spring Web, Spring Data JPA)
- jjwt 0.9.1
- PostgreSQL/MySQL
- Maven 3.6.1

## Setup new Spring Boot project

_pom.xml_

We also need to add one more dependency. – If you want to use PostgreSQL:

```xml

<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <scope>runtime</scope>
</dependency>
```

– or MySQL is your choice:

```xml

<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <scope>runtime</scope>
</dependency>
```

## Configure Spring Datasource, JPA, App properties

- For Postgresql

```
spring.datasource.url= jdbc:postgresql://localhost:5432/testdb
spring.datasource.username= postgres
spring.datasource.password= 123

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation= true
spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQLDialect

# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto= update

# App Properties
bezkoder.app.jwtSecret= keerSecretKey
bezkoder.app.jwtExpirationMs= 86400000
```

- For MySQL

```
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:33060/springboot_jwt?useUnicode=true&characterEncoding=utf8&useSSL=false
spring.datasource.username=springboot
spring.datasource.password=123456
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
# App Properties
bezkoder.app.jwtSecret=keerSecretKey
bezkoder.app.jwtExpirationMs=86400000
```

## Create the models@

- _models/ERole.java_
- _models/Role.java_
- _models/User.java_

## Implement Repositories

- _repository/UserRepository.java_
- _repository/RoleRepository.java_

## Configure Spring Security

_security/WebSecurityConfig.java_

– `@EnableWebSecurity` allows Spring to find and automatically apply the class to the global Web
Security.

– `@EnableGlobalMethodSecurity` provides AOP security on methods. It enables `@PreAuthorize`
, `@PostAuthorize`, it also supports [JSR-250](https://en.wikipedia.org/wiki/JSR_250). You can find
more parameters in configuration in
[Method Security Expressions](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#method-security-expressions)
.

- We override the `configure(HttpSecurity http)` method from `WebSecurityConfigurerAdapter`
  interface. It tells Spring Security how we configure CORS and CSRF, when we want to require all
  users to be authenticated or not, which filter (`AuthTokenFilter`) and when we want it to work (
  filter before
  `UsernamePasswordAuthenticationFilter`), which Exception Handler is chosen (`AuthEntryPointJwt`).

– Spring Security will load User details to perform authentication & authorization. So it has
`UserDetailsService` interface that we need to implement.

– The implementation of UserDetailsService will be used for configuring `DaoAuthenticationProvider`
by
`AuthenticationManagerBuilder.userDetailsService()` method.

– We also need a PasswordEncoder for the `DaoAuthenticationProvider`. If we don’t specify, it will
use plain text.

### Implement UserDetails & UserDetailsService

- _security/services/UserDetailsImpl.java_
- _security/services/UserDetailsServiceImpl.java_

### Filter the Requests

- _security/jwt/AuthTokenFilter.java_

### Create JWT Utility class

- _security/jwt/JwtUtils.java_

### Handle Authentication Exception

- _security/jwt/AuthEntryPointJwt.java_

## Define payloads for Spring RestController

Let me summarize the payloads for our RestAPIs:

**Requests:**

- LoginRequest: { username, password }
- SignupRequest: { username, email, password }

**Responses:**

- JwtResponse: { token, type, id, username, email, roles }
- MessageResponse: { message }

### Create Spring RestAPIs Controllers

- _controllers/AuthController.java_
### Controller for testing Authorization
- _controllers/TestController.java_

## Run & Test
### Create Database and User on MySQL
```mysql-sql
create database springboot_jwt default charset utf8 collate utf8_general_ci;
create user 'springboot'@'%' identified by '123456';
grant all privileges on springboot_jwt.* to "springboot";
flush privileges;
```