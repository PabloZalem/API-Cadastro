# 🧩 Projeto Spring Boot - API de Usuários

Este projeto é uma API RESTful desenvolvida com **Spring Boot**, com o objetivo de realizar operações CRUD (Create, Read, Update, Delete) em uma entidade `Usuário`. Utiliza banco de dados em memória **H2**, **Spring Data JPA** para persistência, **Spring Web** para exposição dos endpoints e **Lombok** para reduzir a verbosidade do código.

## 🚀 Tecnologias e Dependências

- Java 21+
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok

## 📦 Dependências no `pom.xml`

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
