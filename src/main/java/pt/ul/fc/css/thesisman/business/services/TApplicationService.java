package pt.ul.fc.css.thesisman.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeApplicationDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeApplicationSubmissionDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeDTO;
import pt.ul.fc.css.thesisman.business.entities.ThemeApplication;
import pt.ul.fc.css.thesisman.business.entities.theme.Theme;
import pt.ul.fc.css.thesisman.business.entities.user.Student;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;
import pt.ul.fc.css.thesisman.business.repositories.ApplicationRepository;
import pt.ul.fc.css.thesisman.business.repositories.theme.ThemeRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.StudentRepository;

@Service
public class TApplicationService {
  private final ApplicationRepository applicationRepository;
  private final StudentRepository studentRepository;
  private final ThemeRepository themeRepository;

  @Autowired
  public TApplicationService(
      ApplicationRepository applicationRepository,
      StudentRepository studentRepository,
      ThemeRepository themeRepository) {
    this.applicationRepository = applicationRepository;
    this.studentRepository = studentRepository;
    this.themeRepository = themeRepository;
  }

  public Optional<ThemeApplicationDTO> createThemeApplication(
      ThemeApplicationSubmissionDTO themeApplicationDTO) {
    Optional<Student> student = studentRepository.findById(themeApplicationDTO.getStudent());
    if (student.isEmpty()) {
      return Optional.empty();
    }

    Teacher admin = student.get().getMasters().getCoordinator();
    List<Theme> themesList = new ArrayList<>();
    for (ThemeDTO t : themeApplicationDTO.getThemes()) {
      Optional<Theme> theme = themeRepository.findById(t.getId());
      theme.ifPresent(themesList::add);
    }

    ThemeApplication themeApplication = new ThemeApplication(student.get(), admin, themesList);
    applicationRepository.save(themeApplication);
    return Optional.of(themeApplication.toDTO());
  }

  public Optional<ThemeApplicationDTO> findByStudentId(Long id) {
    Optional<ThemeApplication> application = applicationRepository.findByStudentId(id);
    ThemeApplicationDTO applicationDTO = null;
    if (application.isPresent()) applicationDTO = application.get().toDTO();
    return Optional.ofNullable(applicationDTO);
  }

  public Optional<ThemeApplicationDTO> findById(Long id) {
    Optional<ThemeApplication> themeApplication = applicationRepository.findById(id);
    return themeApplication.map(ThemeApplication::toDTO);
  }

  public void deleteById(Long id) {
    applicationRepository.deleteById(id);
  }
}
