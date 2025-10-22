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
```

🏗️ Arquitetura da Aplicação
Este projeto segue a arquitetura em camadas, que promove a separação de responsabilidades e facilita a manutenção e escalabilidade da aplicação.

📚 Camadas
Controller: Responsável por receber as requisições HTTP e encaminhá-las para a camada de negócio.
Business (Service): Contém a lógica de negócio e orquestra as operações entre controller e repository.
Infrastructure:
    Entity: Define os modelos de dados que representam as tabelas no banco de dados. Aqui criamos nossa Ent
    Repository: Interfaces que estendem JpaRepository para realizar operações de persistência.

Estrutura da Entidade Usuario
```java
@Entity
@Table(name = "usuarios")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email", unique = true)
    private String email;
}
``` 

Estrutura Repository
```java
    public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    }
```
Escolhemos trabalhar com essa interface, pois ela sera o nosso facilitador na construção dos metodos, passamos no parametro nome da entidade e o tipo de ID.

Estrutura Service
```java
@Service
public class UsuarioService {
	// Vamos fazer por injecao de dependencia
	private final UsuarioRepository repository;
	
	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public void salvarUsuario(Usuario usuario) {
		repository.saveAndFlush(usuario);
	}
}
```
Ele vai fazer a injecao de dependencia, onde usaremos os metodos do JPARepository para criarmos o nosso CRUD. Passamo a anotacao @Service para indicar ao spring que essa classe é uma service.
Podemos fazer essa interação com a interface repository de três maneira:
1- Pela anotação @Autowired que é do Spring;
2- Pela anotação RequiredArgsConstructor que é do Lombok.
3- Escrevendo um construtor na mão para injetar essa dependencia do repository.
Escolhemos a terceira opção.
