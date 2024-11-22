package pt.ul.fc.css.thesisman.business.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.business.entities.defense.Jury;
import pt.ul.fc.css.thesisman.business.entities.defense.ThesisDefense;
import pt.ul.fc.css.thesisman.business.entities.defense.location.Reservation;
import pt.ul.fc.css.thesisman.business.entities.thesis.Thesis;
import pt.ul.fc.css.thesisman.business.repositories.TDefenseRepository;
import pt.ul.fc.css.thesisman.business.repositories.ThesisRepository;

@Service
public class TDefenseService {

  private final TDefenseRepository tDefenseRepository;
  private final ThesisRepository thesisRepository;

  private final int THESIS_DURATION = 90;

  @Autowired
  public TDefenseService(TDefenseRepository tDefenseRepository, ThesisRepository thesisRepository) {
    this.tDefenseRepository = tDefenseRepository;
    this.thesisRepository = thesisRepository;
  }

  public List<ThesisDefense> findRoomReservations(
      LocalDateTime startDate, LocalDateTime endDate, String type, String place) {
    LocalDateTime startDateMax = startDate.minusMinutes(THESIS_DURATION);
    return tDefenseRepository.findReservations(startDateMax, endDate, type, place);
  }

  public Optional<ThesisDefense> findById(Long id) {
    return tDefenseRepository.findById(id);
  }

  public void createDefense(Reservation reservation, Thesis thesis, Jury jury) {
    ThesisDefense thesisDefense = new ThesisDefense();
    thesisDefense.setReservation(reservation);
    thesisDefense.setThesis(thesis);
    thesisDefense.setJury(jury);
    tDefenseRepository.save(thesisDefense);
    thesis.setDefense(thesisDefense);
    thesisRepository.save(thesis);
  }
}
