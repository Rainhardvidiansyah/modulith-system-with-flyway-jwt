## Model: Anemic Domain Model vs Rich Domain Model

### Definition

*   **Anemic Domain Model**  
    A design approach in which domain objects (entities) serve solely as data containers, devoid of business logic. These objects typically contain only properties, getters, and setters. All business logic, validation, and process rules are separated into external classes, such as a *Service Layer* or *Use Case*.

*   **Rich Domain Model**  
    A design approach in which domain objects encapsulate both data and business behavior within a single object. Entities are fully responsible for validating their own state and expose methods representing real-world business operations, in accordance with *Domain-Driven Design* (DDD) principles.

### Comparison

| Characteristics                          | Anemic Domain Model | Rich Domain Model |
|:-----------------------------------------|:--------------------|:------------------|
| **Business Logic Location**              | Located outside the entity (within the *Service* or *Controller* class) | Located directly within the domain entity object |
| **Entity Condition**                     | Contains only data properties with public *getters* and *setters* | Contains data properties and operational methods (*behavior*) |
| **Encapsulation**                        | Weak; entity state can be freely modified from outside | Strong; state can only be modified through entity methods |
| **Validation Rules (Invariants)**        | Handled by *Service*; risk of invalid data if *Service* is bypassed | Enforced by the entity; object cannot exist in an invalid state |
| **Code Scalability**                     | Hard to maintain in large systems due to logic accumulation in *Service* | Easier to maintain because logic is well distributed across domains |
| **Object Nature**                        | Passive (just a data structure / *Data Transfer Object*) | Active (represents real business concepts) |
| **Project Suitability**                  | Simple applications, CRUD-based, or small-scale projects | Complex applications with rich business rules (DDD) |


# Example of Rich Domain Model:
The `User` entity controls its own data. Public setter access is restricted (or limited), and changes must be made via the `changeEmail` business method, which ensures that business rules (invariants) are always satisfied.

```java
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Entity controls data and its own behaviour (Rich)
@Entity
@Getter // Only Getter, with no setter
public class User {
    @Id
    private Long id;
    private String email;

    // JPA constructor (Protected)
    protected User() {}

    public User(Long id, String email) {
        this.id = id;
        changeEmail(email); // Guarantees validity since the object created
    }

    // Business behaviour is in entity class
    public void changeEmail(String newEmail) {
        // Validate business rule (invariant)
        if (newEmail == null || !newEmail.contains("@")) {
            throw new IllegalArgumentException("Email format not valid");
        }
        this.email = newEmail;
    }
}

// Pure Service Layer acts as coordinator (Application Service)
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void updateUserEmail(Long userId, String newEmail) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Service delegates tasks to domain entity 
        user.changeEmail(newEmail); 
        
        userRepository.save(user);
    }
}
```

## Configuration - application.yml

```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/<table_name>
    username: your_database_name
    password: your_database_password
    driver-class-name: org.postgresql.Driver
  flyway:
    enabled: true
    locations:
     - classpath:db/migration/global
     - classpath:db/migration/product
     - classpath:db/migration/inventory
  jpa:
    hibernate:
      ddl-auto: none
  modulith:
    runtime:
      flyway-enabled: true
    events:
        jdbc:
          schema-initialization:
            enabled: true
  mvc:
    throw-exception-if-no-handler-found: true
  web:
    resource:
      add-mappings: false
  autoconfigure:
    exclude: org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration

server:
 servlet:
  context-path: /api/v1

logging:
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - [ReqID: %X{requestId}] - %msg%n"

```

## To Be Continued