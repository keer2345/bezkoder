package com.keer.spring;

import static org.assertj.core.api.Assertions.assertThat;

import com.keer.spring.entity.Tutorial;
import com.keer.spring.repository.TutorialRepo;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class JpaUnitTest {
  @Autowired private TestEntityManager entityManager;
  @Autowired private TutorialRepo tutorialRepo;

  @Test
  public void shouldFindNoTutorialsIfRepositoryIsEmpty() {
    Iterable<Tutorial> tutorials = tutorialRepo.findAll();
    assertThat(tutorials).isEmpty();
  }

  @Test
  public void shouldStoreATutorial() {
    Tutorial tutorial = tutorialRepo.save(new Tutorial("title1", "desc1", true));

    assertThat(tutorial).hasFieldOrPropertyWithValue("title", "title1");
    assertThat(tutorial).hasFieldOrPropertyWithValue("description", "desc1");
    assertThat(tutorial).hasFieldOrPropertyWithValue("published", true);
  }

  @Test
  public void shouldFindAllTutorials() {
    Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
    entityManager.persist(tut1);

    Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
    entityManager.persist(tut2);

    Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
    entityManager.persist(tut3);

    List<Tutorial> tutorials = tutorialRepo.findAll();
    assertThat(tutorials).hasSize(3).contains(tut1, tut2, tut3);
  }

  @Test
  public void shouldFindTutorialById() {
    Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
    entityManager.persist(tut1);

    Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
    entityManager.persist(tut2);

    Tutorial foundTutorial = tutorialRepo.findById(tut2.getId()).get();

    assertThat(foundTutorial).isEqualTo(tut2);
  }

  @Test
  public void shouldFindPublishedTutorials() {
    Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
    entityManager.persist(tut1);

    Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
    entityManager.persist(tut2);

    Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
    entityManager.persist(tut3);

    Iterable<Tutorial> tutorials = tutorialRepo.findByPublished(true, null);

    assertThat(tutorials).hasSize(2).contains(tut1, tut3);
  }

  @Test
  public void shouldFindTutorialsByTitleContainingString() {
    Tutorial tut1 = new Tutorial("Spring Boot Tut#1", "Desc#1", true);
    entityManager.persist(tut1);

    Tutorial tut2 = new Tutorial("Java Tut#2", "Desc#2", false);
    entityManager.persist(tut2);

    Tutorial tut3 = new Tutorial("Spring Data JPA Tut#3", "Desc#3", true);
    entityManager.persist(tut3);

    Iterable<Tutorial> tutorials = tutorialRepo.findByTitleContaining("ring", (Pageable) null);

    assertThat(tutorials).hasSize(2).contains(tut1, tut3);
  }

  @Test
  public void shouldUpdateTutorialById() {
    Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
    entityManager.persist(tut1);

    Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
    entityManager.persist(tut2);

    Tutorial updatedTut = new Tutorial("updated Tut#2", "updated Desc#2", true);

    Tutorial tut = tutorialRepo.findById(tut2.getId()).get();
    tut.setTitle(updatedTut.getTitle());
    tut.setDescription(updatedTut.getDescription());
    tut.setPublished(updatedTut.isPublished());
    tutorialRepo.save(tut);

    Tutorial checkTut = tutorialRepo.findById(tut2.getId()).get();

    assertThat(checkTut.getId()).isEqualTo(tut2.getId());
    assertThat(checkTut.getTitle()).isEqualTo(updatedTut.getTitle());
    assertThat(checkTut.getDescription()).isEqualTo(updatedTut.getDescription());
    assertThat(checkTut.isPublished()).isEqualTo(updatedTut.isPublished());
  }

  @Test
  public void shouldDeleteTutorialById() {
    Tutorial tut1 = new Tutorial("Tut#1", "Desc#1", true);
    entityManager.persist(tut1);

    Tutorial tut2 = new Tutorial("Tut#2", "Desc#2", false);
    entityManager.persist(tut2);

    Tutorial tut3 = new Tutorial("Tut#3", "Desc#3", true);
    entityManager.persist(tut3);

    tutorialRepo.deleteById(tut2.getId());

    Iterable<Tutorial> tutorials = tutorialRepo.findAll();

    assertThat(tutorials).hasSize(2).contains(tut1, tut3);
  }

  @Test
  public void shouldDeleteAllTutorials() {
    entityManager.persist(new Tutorial("Tut#1", "Desc#1", true));
    entityManager.persist(new Tutorial("Tut#2", "Desc#2", false));

    tutorialRepo.deleteAll();

    assertThat(tutorialRepo.findAll()).isEmpty();
  }
}
