package com.keer.spring.controller;

import com.keer.spring.entity.Tutorial;
import com.keer.spring.repository.TutorialRepo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {
  @Autowired private TutorialRepo tutorialRepo;

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

  @GetMapping("/{id}")
  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
    Optional<Tutorial> tutorialData = tutorialRepo.findById(id);
    if (tutorialData.isPresent()) {
      return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping()
  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial data) {
    try {
      Tutorial tutorial = new Tutorial(data.getTitle(), data.getDescription(), false);
      tutorialRepo.save(tutorial);
      return new ResponseEntity<>(tutorial, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Tutorial> updateTutorial(
      @PathVariable("id") long id, @RequestBody Tutorial data) {
    Optional<Tutorial> tutorialData = tutorialRepo.findById(id);
    if (tutorialData.isPresent()) {
      Tutorial tutorial = tutorialData.get();
      tutorial.setTitle(data.getTitle());
      tutorial.setDescription(data.getDescription());
      tutorial.setPublished(data.isPublished());

      return new ResponseEntity<>(tutorialRepo.save(tutorial), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Tutorial> deleteById(@PathVariable("id") long id) {
    try {
      tutorialRepo.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping()
  public ResponseEntity<Tutorial> deleteAllTutorials() {
    try {
      tutorialRepo.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
