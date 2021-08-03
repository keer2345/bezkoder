- [Spring Boot + React: JWT Authentication with Spring Security](#spring-boot--react-jwt-authentication-with-spring-security)
- [Materials](#materials)
  - [JWT (JSON Web Token)](#jwt-json-web-token)
  - [Flow for User Registration and User Login](#flow-for-user-registration-and-user-login)
  - [Spring Boot & Spring Security for Back-end](#spring-boot--spring-security-for-back-end)
    - [Implementation](#implementation)
  - [React, React Router for Front-end](#react-react-router-for-front-end)
    - [Implementation](#implementation-1)
  - [Integrate SpringBoot and React](#integrate-springboot-and-react)

# [Spring Boot + React: JWT Authentication with Spring Security](https://www.bezkoder.com/spring-boot-react-jwt-auth/)

**My Practice Source Code**

# Materials
- [In-depth Introduction to JWT-JSON Web Token](https://www.bezkoder.com/jwt-json-web-token/)
## JWT (JSON Web Token)
![](imgs/01.png)
## Flow for User Registration and User Login
![](imgs/02.png)
## Spring Boot & Spring Security for Back-end
![](imgs/03.png)

**Spring Security**

– `WebSecurityConfigurerAdapter` is the crux of our security implementation. It provides HttpSecurity configurations to configure cors, csrf, session management, rules for protected resources. We can also extend and customize the default configuration that contains the elements below.

– `UserDetailsService` interface has a method to load User by _username_ and returns a `UserDetails` object that Spring Security can use for authentication and validation.

– `UserDetails` contains necessary information (such as: username, password, authorities) to build an Authentication object.

– `UsernamePasswordAuthenticationToken` gets {username, password} from login Request, `AuthenticationManager` will use it to authenticate a login account.

– `AuthenticationManager` has a `DaoAuthenticationProvider` (with help of `UserDetailsService` & `PasswordEncoder`) to validate `UsernamePasswordAuthenticationToken` object. If successful, `AuthenticationManager` returns a fully populated Authentication object (including granted authorities).

– `OncePerRequestFilter` makes a single execution for each request to our API. It provides a doFilterInternal() method that we will implement parsing & validating JWT, loading User details (using UserDetailsService), checking Authorizaion (using UsernamePasswordAuthenticationToken).

– `AuthenticationEntryPoint` will catch unauthorized error and return a 401 when Clients access protected resources without authentication.

**Repository** contains `UserRepository` & `RoleRepository` to work with Database, will be imported into Controller.

**Controller** receives and handles request after it was filtered by `OncePerRequestFilter`.
– `AuthController` handles signup/login requests
– `TestController` has accessing protected resource methods with role based validations.

### Implementation

You can find step by step to implement this Spring Boot – Spring Security App in the post:
[Secure Spring Boot App with Spring Security & JWT Authentication](https://bezkoder.com/spring-boot-jwt-authentication/)

For working with MongoDB:
[Spring Boot, MongoDB: JWT Authentication with Spring Security](https://bezkoder.com/spring-boot-jwt-auth-mongodb/)

Or PostgreSQL:
[Spring Boot, Spring Security, PostgreSQL: JWT Authentication example](https://bezkoder.com/spring-boot-security-postgresql-jwt-authentication/)

## React, React Router for Front-end

![](imgs/04.png)

### Implementation

You can find step by step to implement this React App in the post:
[React JWT Authentication (without Redux) example](https://bezkoder.com/react-jwt-auth/)

Using Hooks:
[React Hooks: JWT Authentication (without Redux) example](https://bezkoder.com/react-hooks-jwt-auth/)

Or Redux:
[React Redux: JWT Authentication & Authorization example](https://bezkoder.com/react-redux-jwt-auth/)

Or Hooks + Redux:
[React Hooks + Redux: JWT Authentication & Authorization example](https://bezkoder.com/react-hooks-redux-login-registration-example/)

## Integrate SpringBoot and React

[How to integrate React.js with Spring Boot](https://bezkoder.com/integrate-reactjs-spring-boot/)