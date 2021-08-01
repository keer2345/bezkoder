package com.keer.spring.service;

import com.keer.spring.entity.Tutorial;
import com.keer.spring.repository.TutorialRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorialService {
  @Autowired private TutorialRepo tutorialRepo;

}
