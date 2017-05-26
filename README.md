########################################################################################
@Author Andrea Franceschini (a.franceschini@reply.it  &  atariw@gmail.com)
@Author RedFroggy, Michael DESIGAUD  (refer to address below)

WEB-SEED application based on Spring, Angular2 and a custom HMAC authentication (upon Spring Security)

You can use this seed to start writing a new Java Web Application out of the box.

In January 2017 we forked the application of RedFroggy (https://github.com/RedFroggy/angular-spring-hmac/tree/angular2)
and extended the RedFroggy application with a "subscription" mechanism and a JDBC interface to common databases.
Such subscription mechanism is completed with an e-mail confirmation system and a Captcha.

#########################################################################################


#HMAC Implementation using Spring and Angular2 

#Stack
- Spring Boot
- Spring Security
- Spring MVC
- Springt JDBC
- Angular 2.2.0

#Features
- Token based authentication
- Json Web Token  
- HMAC implementation
- HMAC Filter used by Spring Security
- HMAC Factory for AngularJS
- Security utility class

#Credentials
admin/frog => role ADMIN
manager/frog => role MANAGER
user/frog => role USER

#To run Java unit tests
````bash
$ mvn test
````

#To run the application
````bash
$ mvn spring-boot:run
````
- Npm modules should be automatically installed and typescript files compiled (see pom.xml file)
- Then go to http://localhost:8080
