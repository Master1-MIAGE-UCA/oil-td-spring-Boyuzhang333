# README.md

## Description
Ce projet est une application Spring Boot pour gérer les lancés de dés. L'application permet de :
- Lancer un ou plusieurs dés et sauvegarder les résultats dans une base de données.
- Consulter l'historique des lancés.
- Utiliser Swagger pour documenter les endpoints.
- Simplifier le code avec Lombok.

---

## Prérequis
1. **Java** : JDK 11 ou version ultérieure.
2. **Maven ou Gradle** : Pour la gestion des dépendances.
3. **IDE** : IntelliJ IDEA, Eclipse ou autre.
4. **Postman ou cURL** : Pour tester les endpoints REST.

---

## Étapes réalisées

### 1. Création du projet Spring Boot
- Utilisation de Spring Initializr pour générer un projet.
- Ajout des dépendances suivantes :
  - Spring Web
  - Spring Data JPA
  - H2 Database

### 2. Configuration du projet
- Configuration du port dans le fichier `application.properties` :
  ```properties
  server.port=8081
  spring.h2.console.enabled=true
  spring.h2.console.path=/h2-console
  spring.datasource.url=jdbc:h2:mem:testdb
  spring.datasource.driverClassName=org.h2.Driver
  spring.datasource.username=sa
  spring.datasource.password=
  spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
  ```

### 3. Création de l'entité DiceRollLog
- Entité JPA comprenant les champs suivants :
  - `id` : Identifiant unique (généré automatiquement).
  - `diceCount` : Nombre de dés lancés.
  - `results` : Liste des résultats obtenus (collection).
  - `timestamp` : Date et heure du lancé.

**Code :**
```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiceRollLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int diceCount;

    @ElementCollection
    private List<Integer> results;

    private LocalDateTime timestamp;
}
```

### 4. Création du Repository
- Interface héritant de `JpaRepository` pour gérer les interactions avec la base de données.
```java
public interface DiceRollLogRepository extends JpaRepository<DiceRollLog, Long> {
}
```

### 5. Création du Service
- Service marqué avec `@Service` contenant la logique métier pour :
  - Générer les résultats des lancés.
  - Sauvegarder les résultats dans la base de données.

**Code :**
```java
@Service
public class DiceRollService {

    private final DiceRollLogRepository repository;
    private final Random random = new Random();

    @Autowired
    public DiceRollService(DiceRollLogRepository repository) {
        this.repository = repository;
    }

    public List<Integer> rollDiceAndSave(int count) {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            results.add(random.nextInt(6) + 1);
        }
        saveLog(count, results);
        return results;
    }

    private void saveLog(int count, List<Integer> results) {
        DiceRollLog log = new DiceRollLog(count, results, LocalDateTime.now());
        repository.save(log);
    }
}
```

### 6. Création du Contrôleur
- Contrôleur REST pour exposer les endpoints :
  - `GET /api/rollDice` : Lancer un seul dé.
  - `GET /api/rollDices/{X}` : Lancer X dés.
  - `GET /api/diceLogs` : Afficher l'historique des lancés.

**Code :**
```java
@RestController
@RequestMapping("/api")
public class DiceRollController {

    private final DiceRollService diceRollService;

    @Autowired
    public DiceRollController(DiceRollService diceRollService) {
        this.diceRollService = diceRollService;
    }

    @GetMapping("/rollDice")
    public List<Integer> rollSingleDice() {
        return diceRollService.rollDiceAndSave(1);
    }

    @GetMapping("/rollDices/{count}")
    public List<Integer> rollMultipleDice(@PathVariable int count) {
        return diceRollService.rollDiceAndSave(count);
    }

    @GetMapping("/diceLogs")
    public List<DiceRollLog> getAllDiceLogs() {
        return diceRollService.getAllLogs();
    }
}
```

### 7. Intégration de Swagger
- Ajout de la dépendance Swagger :
  ```xml
  <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-ui</artifactId>
      <version>1.7.0</version>
  </dependency>
  ```
- Accès à la documentation interactive via : `http://localhost:8081/swagger-ui.html`.

### 8. Simplification avec Lombok
- Utilisation des annotations :
  - `@Data` : Génération automatique des getters, setters, `toString`, etc.
  - `@NoArgsConstructor` : Génération d'un constructeur sans arguments.
  - `@AllArgsConstructor` : Génération d'un constructeur avec tous les arguments.

---

## Exécution et Test
1. **Démarrer l'application** :
   ```bash
   ./mvnw spring-boot:run
   ```
2. **Tester les endpoints** :
  - `GET /api/rollDice` : Retourne un résultat aléatoire pour un dé.
  - `GET /api/rollDices/{count}` : Retourne les résultats pour plusieurs dés.
  - `GET /api/diceLogs` : Retourne l'historique des lancés au format JSON.
3. **Consulter Swagger** : Accédez à `http://localhost:8081/swagger-ui.html` pour visualiser la documentation interactive.
4. **Accéder à H2 Console** : Accédez à `http://localhost:8081/h2-console` pour interagir avec la base de données.


[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/WCHp-cRl)
# Projet "Dice" - Gestion de lancés de dés avec Spring Boot

## Description
Le projet "Dice" est une application construite avec Spring Boot permettant de simuler des lancés de dés et de gérer un historique des résultats en base de données. Ce projet met en œuvre les concepts fondamentaux de Spring Boot, notamment l'injection de dépendances, les services RESTful, les entités JPA et les repositories.


## Étapes de réalisation

### 1. Création du projet Spring Boot
- Utilisez [Spring Initializr](https://start.spring.io/) pour créer le projet.
- Choisissez la dernière version de Spring Boot disponible (LTS).
- Optez pour **Maven** ou **Gradle** comme outil de gestion de dépendances.
- Ajoutez les dépendances nécessaires : **Spring Web**, **Spring Data JPA**, **H2 Database** .

### 2. Configuration du projet
- Configurez l'application pour qu'elle utilise le port **8081**.
- Donnez un nom (**dice**) au projet dans le fichier de configuration :
  - Utilisez **`application.properties`** ou **`application.yml`** selon votre préférence.

### 3. Création de la classe `Dice`
- Implémentez une classe représentant un dé avec les méthodes nécessaires pour effectuer un lancé.
- Marquez cette classe avec l'annotation `@Component` pour pouvoir l'injecter au besoin.

### 4. Création de l'entité `DiceRollLog`
- Modélisez une entité JPA `DiceRollLog` comprenant les champs suivants :
  - **`id`** : Identifiant unique.
  - **`diceCount`** : Nombre de dés lancés.
  - **`results`** : Liste ou chaîne des valeurs obtenues. Il existe de nombreuses façons de stocker des valeurs simples (simple String), certaines sont plus élégantes (@ElementCollection) que d'autres, vous pouvez choisir la solution qui vous conviendra.
  - **`timestamp`** : Horodatage du lancé.
- Utilisez des annotations JPA comme `@Entity`, `@Id`, `@GeneratedValue`, etc.

### 5. Création du `Repository`
- Implémentez une interface héritant de `JpaRepository<DiceRollLog, Long>` pour gérer les interactions avec la base de données.

### 6. Création du contrôleur REST pour lancer les dés
- Implémentez un contrôleur REST annoté avec `@RestController`.
- Ajoutez les endpoints suivants :
  - **`GET /rollDice`** : Lancer un seul dé.
  - **`GET /rollDices/{X}`** : Lancer X dés (X étant un paramètre dynamique).

### 7. Création du `Service`
- Créez un service marqué avec `@Service` contenant une méthode :
  - Prend en paramètre le nombre de dés à lancer.
  - Retourne les résultats des lancés au contrôleur.
  - Enregistre l’historique des lancés dans la base via le `Repository`.

### 8. Contrôleur pour afficher les historiques
- Ajoutez un autre contrôleur REST permettant d'afficher l'historique des lancés :
  - **`GET /diceLogs`** : Retourne tous les enregistrements de `DiceRollLog` au format JSON.

### 9. Tests et validation
- Démarrez l'application et testez les endpoints.
- Vérifiez les résultats dans la base de données et les réponses JSON.

### 10. (Bonus) Ajout de fonctionnalités avancées
- **Swagger** :
  - Ajoutez la dépendance Swagger/OpenAPI.
  - Configurez Swagger pour documenter vos endpoints.
  - Accédez à la documentation sur **`http://localhost:8081/swagger-ui.html`**.
- **Lombok** :
  - Utilisez Lombok pour simplifier les getters, setters et constructeurs de vos entités.

---

## Livrables
- Le code complet du projet, accessible via un dépôt GitHub.
- Un fichier `README.md` décrivant les étapes réalisées

## Technologies
- **Framework principal** : Spring Boot
- **Base de données** : H2 
- **Documentation API** : Swagger (bonus)
- **Simplification de code** : Lombok (bonus)

