package pt.ul.fc.css.thesisman.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.business.dtos.masters.MastersDegreeDTO;
import pt.ul.fc.css.thesisman.business.entities.MastersDegree;
import pt.ul.fc.css.thesisman.business.repositories.MastersDegreeRepository;

@Service
public class MastersDegreeService {

  private final MastersDegreeRepository mastersDegreeRepository;

  @Autowired
  public MastersDegreeService(MastersDegreeRepository mastersDegreeRepository) {
    this.mastersDegreeRepository = mastersDegreeRepository;
  }

  public List<MastersDegreeDTO> findAll() {
    List<MastersDegree> mastersDegrees = mastersDegreeRepository.findAll();
    List<MastersDegreeDTO> dtos = new ArrayList<>();
    for (MastersDegree m : mastersDegrees) dtos.add(m.toDTO());
    return dtos;
  }

  public Optional<MastersDegree> findByCoordinatorName(String name) {
    return mastersDegreeRepository.findByCoordinatorName(name);
  }
}
