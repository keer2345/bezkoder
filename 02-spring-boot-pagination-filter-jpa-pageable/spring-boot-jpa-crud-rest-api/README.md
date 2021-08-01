- [spring-boot-jpa-crud-rest-api](https://www.bezkoder.com/spring-boot-jpa-crud-rest-api/)
- [Spring Boot Pagination & Filter example | Spring JPA, Pageable](https://bezkoder.com/spring-boot-pagination-filter-jpa-pageable/)
- [Spring Data JPA Sort/Order by multiple Columns | Spring Boot](https://bezkoder.com/spring-data-sort-multiple-columns/)
- [Spring Boot Unit Test for JPA Repositiory with @DataJpaTest](https://bezkoder.com/spring-boot-unit-test-jpa-repo-datajpatest/)

# CRUD Rest API

## Repository

```java

@Repository
public interface TutorialRepo extends JpaRepository<Tutorial, Long> {

  List<Tutorial> findByPublished(boolean published);

  List<Tutorial> findByTitleContaining(String title);
}
```

## Controller

```java

@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {

  @Autowired
  private TutorialRepo tutorialRepo;

  @GetMapping()
  public ResponseEntity<List<Tutorial>> getAllTutorials(
      @RequestParam(required = false) String title) {
    try {
      List<Tutorial> tutorials = new ArrayList<Tutorial>();
      if (title == null) {
        tutorialRepo.findAll().forEach(tutorials::add);
      } else {
        tutorialRepo.findByTitleContaining(title).forEach(tutorials::add);
      }
      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/published")
  public ResponseEntity<List<Tutorial>> getTutorialByPublished() {
    try {
      List<Tutorial> tutorials = tutorialRepo.findByPublished(true);
      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {/*...*/}

  @PostMapping()
  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial data) {/*...*/}

  @PutMapping("/{id}")
  public ResponseEntity<Tutorial> updateTutorial(
      @PathVariable("id") long id, @RequestBody Tutorial data) {/*...*/}

  @DeleteMapping("/{id}")
  public ResponseEntity<Tutorial> deleteById(@PathVariable("id") long id) {/*...*/}

  @DeleteMapping()
  public ResponseEntity<Tutorial> deleteAllTutorials() {/*...*/}
}
```

# Pagination & Filter JPA Pageable

## Repository

```java

@Repository
public interface TutorialRepo extends JpaRepository<Tutorial, Long> {

  Page<Tutorial> findByPublished(boolean published, Pageable pageable);

  Page<Tutorial> findByTitleContaining(String title, Pageable pageable);
}
```

## Controller

```java

@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {

  @Autowired
  private TutorialRepo tutorialRepo;

  @GetMapping()
  public ResponseEntity<Map<String, Object>> getAllTutorials(
      @RequestParam(required = false) String title,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size) {
    try {
      List<Tutorial> tutorials = new ArrayList<Tutorial>();
      Pageable paging = PageRequest.of(page, size);
      Page<Tutorial> pageTutorials;

      if (title == null) {
        pageTutorials = tutorialRepo.findAll(paging);
      } else {
        pageTutorials = tutorialRepo.findByTitleContaining(title, paging);
      }

      tutorials = pageTutorials.getContent();

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      Map<String, Object> response = new HashMap<>();
      response.put("tutorials", tutorials);
      response.put("currentPage", pageTutorials.getNumber());
      response.put("totalItems", pageTutorials.getTotalElements());
      response.put("totalPages", pageTutorials.getTotalPages());

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/published")
  public ResponseEntity<Map<String, Object>> getTutorialByPublished(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
    try {
      List<Tutorial> tutorials = new ArrayList<>();
      Pageable paging = PageRequest.of(page, size);
      Page<Tutorial> pageTutorials = tutorialRepo.findByPublished(true, paging);
      tutorials = pageTutorials.getContent();

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      Map<String, Object> response = new HashMap<>();
      response.put("tutorials", tutorials);
      response.put("currentPage", pageTutorials.getNumber());
      response.put("totalItems", pageTutorials.getTotalElements());
      response.put("totalPages", pageTutorials.getTotalPages());

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // ...
}
```