<!-- markdown-toc start - Don't edit this section. Run M-x markdown-toc-refresh-toc -->
**Table of Contents**

- [Materials](#materials)
- [CRUD Rest API](#crud-rest-api)
    - [Repository](#repository)
    - [Controller](#controller)
- [Pagination & Filter JPA Pageable](#pagination--filter-jpa-pageable)
    - [Repository](#repository-1)
    - [Controller](#controller-1)
- [Spring Data JPA Sort/Order by multiple Columns](#spring-data-jpa-sortorder-by-multiple-columns)
    - [Example:](#example)
        - [Spring Data Sort and Order](#spring-data-sort-and-order)
        - [Paging and Sorting](#paging-and-sorting)
    - [Practice](#practice)
        - [Repository that supports Pagination and Sorting](#repository-that-supports-pagination-and-sorting)
        - [Controller with Pagination & Sorting](#controller-with-pagination--sorting)

<!-- markdown-toc end -->

# Materials

- [spring-boot-jpa-crud-rest-api](https://www.bezkoder.com/spring-boot-jpa-crud-rest-api/)
- [Spring Boot Pagination & Filter example | Spring JPA, Pageable](https://bezkoder.com/spring-boot-pagination-filter-jpa-pageable/)
- [Spring Data JPA Sort/Order by multiple Columns | Spring Boot](https://bezkoder.com/spring-data-sort-multiple-columns/)
- [Spring Boot Pagination and Sorting example](https://www.bezkoder.com/spring-boot-pagination-sorting-example/)
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

# Spring Data JPA Sort/Order by multiple Columns

## Example:

### Spring Data Sort and Order

```java
public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {

  Iterable<T> findAll(Sort sort);
}
```

```java
List<Tutorial> findByTitleContaining(String title,Sort sort);
```

```java
// order by 'published' column - ascending
List<Tutorial> tutorials=
    tutorialRepository.findAll(Sort.by("published"));

// order by 'published' column, descending
    List<Tutorial> tutorials=
    tutorialRepository.findAll(Sort.by("published").descending());

// order by 'published' column - descending, then order by 'title' - ascending
    List<Tutorial> tutorials=
    tutorialRepository.findAll(Sort.by("published").descending().and(Sort.by("title")));
```

We can also create a new Sort object with List of Order objects.

```java
List<Order> orders=new ArrayList<Order>();

    Order order1=new Order(Sort.Direction.DESC,"published");
    orders.add(order1);

    Order order2=new Order(Sort.Direction.ASC,"title");
    orders.add(order2);

    List<Tutorial> tutorials=tutorialRepository.findAll(Sort.by(orders));
```

### Paging and Sorting

```java
public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {

  Page<T> findAll(Pageable pageable);
}
```

```java
Page<Tutorial> findByPublished(boolean published,Pageable pageable);
    Page<Tutorial> findByTitleContaining(String title,Pageable pageable);
```

Let’s notice the `Pageable` parameter in Repository methods above. Spring Data infrastructure will
recognizes this parameter automatically to apply pagination and sorting to database.

The Pageable interface contains the information about the requested page such as the size, the
number of the page, or sort information with Sort object.

```java
public interface Pageable {

  int getPageNumber();

  int getPageSize();

  long getOffset();

  Sort getSort();

  Pageable next();

  Pageable previousOrFirst();

  Pageable first();

  boolean hasPrevious();
  ...
}
```

## Practice

### Repository that supports Pagination and Sorting

### Controller with Pagination & Sorting

To get multiple sort request parameters, we use `@RequestParam String[] sort with defaultValue = "
id,desc"`.

Before writing the Controller method to handle the case, let’s see what we retrieve with the
parameters:

- `?sort=column1,direction1`: sorting single column `String[] sort` is an array with 2
  elements: `[“column1”, “direction1”]`
- `?sort=column1,direction1&sort=column2,direction2 `: sorting multiple columns `String[] sort` is
  also an array with 2 elements: `[“column1, direction1”, “column2, direction2”]`

That’s why we need to check if the first item in the array contains `","` or not.

We also need to convert `"asc"/"desc"` into `Sort.Direction.ASC/Sort.Direction.DES` for working
with `Sort.Order` class.

Generally, in the HTTP request URLs, paging parameters are optional. So if our Rest API supports
pagination, we should provide default values to make paging work even when Client does not specify
these parameters.

**Repository:**

```java

@Repository
public interface TutorialRepo extends JpaRepository<Tutorial, Long> {

  Page<Tutorial> findByPublished(boolean published, Pageable pageable);

  Page<Tutorial> findByTitleContaining(String title, Pageable pageable);

  List<Tutorial> findByTitleContaining(String title, Sort sort);
}
```

**Controller:**

```java

@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {

  @Autowired
  private TutorialRepo tutorialRepo;

  private Sort.Direction getSortDirection(String direction) {
    if (direction.toUpperCase().equals("ASC")) {
      return Direction.ASC;
    } else if (direction.toUpperCase().equals("DESC")) {
      return Direction.DESC;
    }
    return Direction.ASC;
  }

  @GetMapping()
  public ResponseEntity<Map<String, Object>> getAllTutorials(
      @RequestParam(required = false) String title,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size,
      @RequestParam(defaultValue = "id,desc") String[] sort) {
    try {
      List<Order> orders = new ArrayList<>();

      if (sort[0].contains(",")) {
        // will sort more than 2 fields
        // sortOrder="field, direction"
        for (String sortOrder : sort) {
          String[] _sort = sortOrder.split(",");
          orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
        }
      } else {
        // sort=[field, direction]
        orders.add(new Order(getSortDirection(sort[1]), sort[0]));
      }

      List<Tutorial> tutorials = new ArrayList<Tutorial>();
      Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
      Page<Tutorial> pageTutorials;

      if (title == null) {
        pageTutorials = tutorialRepo.findAll(pagingSort);
      } else {
        pageTutorials = tutorialRepo.findByTitleContaining(title, pagingSort);
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

  // ...
}
```

Access to:

```
http://127.0.0.1:8080/api/tutorials?page=0&size=12&sort=description,desc&sort=title,asc&sort=id,asc
```
