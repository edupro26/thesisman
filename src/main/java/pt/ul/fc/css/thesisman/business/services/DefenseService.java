package pt.ul.fc.css.thesisman.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.business.dtos.DefenseDTO;
import pt.ul.fc.css.thesisman.business.entities.defense.Defense;
import pt.ul.fc.css.thesisman.business.entities.defense.ProposalDefense;
import pt.ul.fc.css.thesisman.business.entities.defense.ThesisDefense;
import pt.ul.fc.css.thesisman.business.repositories.DefenseRepository;
import pt.ul.fc.css.thesisman.business.repositories.PDefenseRepository;
import pt.ul.fc.css.thesisman.business.repositories.TDefenseRepository;

@Service
public class DefenseService {

  private final DefenseRepository defenseRepository;
  private final TDefenseRepository tDefenseRepository;
  private final PDefenseRepository pDefenseRepository;

  @Autowired
  public DefenseService(
      DefenseRepository defenseRepository,
      TDefenseRepository tDefenseRepository,
      PDefenseRepository pDefenseRepository) {
    this.defenseRepository = defenseRepository;
    this.tDefenseRepository = tDefenseRepository;
    this.pDefenseRepository = pDefenseRepository;
  }

  public List<ThesisDefense> findAllThesisDefenses() {
    return tDefenseRepository.findAll();
  }

  public List<ProposalDefense> findAllProposalDefenses() {
    return pDefenseRepository.findAll();
  }

  public Optional<Defense> findDefenseById(Long id) {
    return defenseRepository.findById(id);
  }

  public List<DefenseDTO> findByInternalTeacher(Long internalTeacherId) {
    List<DefenseDTO> defenseDTOS = new ArrayList<>();

    List<ProposalDefense> proposalDefenses =
        pDefenseRepository.findByInternalTeacher(internalTeacherId);

    for (ProposalDefense proposalDefense : proposalDefenses) {
      defenseDTOS.add(proposalDefense.toDTO());
    }

    return defenseDTOS;
  }

  public List<DefenseDTO> findByPresident(Long presidentId) {
    List<DefenseDTO> defenseDTOS = new ArrayList<>();

    List<ThesisDefense> thesisDefenses = tDefenseRepository.findByPresident(presidentId);

    for (ThesisDefense thesisDefense : thesisDefenses) {
      defenseDTOS.add(thesisDefense.toDTO());
    }

    return defenseDTOS;
  }

  public void saveDefense(Defense defense) {
    defenseRepository.save(defense);
  }
}
