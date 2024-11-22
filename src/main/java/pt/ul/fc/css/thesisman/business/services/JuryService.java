package pt.ul.fc.css.thesisman.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.business.entities.defense.Jury;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;
import pt.ul.fc.css.thesisman.business.repositories.JuryRepository;

@Service
public class JuryService {

  private final JuryRepository juryRepository;

  @Autowired
  public JuryService(JuryRepository juryRepository) {
    this.juryRepository = juryRepository;
  }

  public Jury createJury(Teacher internalAdvisor, Teacher arguer, Teacher president) {
    Jury jury = new Jury(internalAdvisor, arguer, president);
    juryRepository.save(jury);
    return jury;
  }
}
