package pt.ul.fc.css.thesisman.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.business.dtos.ThesisDTO;
import pt.ul.fc.css.thesisman.business.entities.theme.DissertationTheme;
import pt.ul.fc.css.thesisman.business.entities.theme.ProjectTheme;
import pt.ul.fc.css.thesisman.business.entities.theme.Theme;
import pt.ul.fc.css.thesisman.business.entities.thesis.Dissertation;
import pt.ul.fc.css.thesisman.business.entities.thesis.Project;
import pt.ul.fc.css.thesisman.business.entities.thesis.Thesis;
import pt.ul.fc.css.thesisman.business.entities.user.Student;
import pt.ul.fc.css.thesisman.business.repositories.ThesisRepository;
import pt.ul.fc.css.thesisman.business.repositories.theme.ThemeRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.StudentRepository;

@Service
public class ThesisService {

  private final ThesisRepository thesisRepository;
  private final StudentRepository studentRepository;
  private final ThemeRepository themeRepository;

  @Autowired
  public ThesisService(
      ThesisRepository thesisRepository,
      StudentRepository studentRepository,
      ThemeRepository themeRepository) {
    this.thesisRepository = thesisRepository;
    this.studentRepository = studentRepository;
    this.themeRepository = themeRepository;
  }

  public List<ThesisDTO> findAllNotGradedThesesAndInternal(Long internal) {
    List<Thesis> theses = thesisRepository.findThesesWithoutDefenseGradeAndInternal(internal);

    List<ThesisDTO> thesesDTO = new ArrayList<>();

    for (Thesis thesis : theses) {
      thesesDTO.add(thesis.toDTO());
    }

    return thesesDTO;
  }

  public List<Thesis> findAllThesis() {
    return thesisRepository.findAll();
  }

  public Thesis findById(Long id) {
    return thesisRepository.findById(id).orElse(null);
  }

  public Optional<ThesisDTO> findByStudentId(Long id) {
    Optional<Thesis> thesis = thesisRepository.findByStudentId(id);
    ThesisDTO thesisDTO = null;
    if (thesis.isPresent()) thesisDTO = thesis.get().toDTO();
    return Optional.ofNullable(thesisDTO);
  }

  public void createThesis(Long studentId, Long themeId) {
    Student student = studentRepository.findById(studentId).get();

    Theme theme = themeRepository.findById(themeId).get();

    if (theme instanceof ProjectTheme) {
      createProjectThesis(student, (ProjectTheme) theme);
    } else if (theme instanceof DissertationTheme) {
      createDissertationThesis(student, (DissertationTheme) theme);
    }
  }

  public void createProjectThesis(Student student, ProjectTheme theme) {
    Project project = new Project(student, theme, null);
    thesisRepository.save(project);
  }

  public void createDissertationThesis(Student student, DissertationTheme theme) {
    Dissertation project = new Dissertation(student, theme, null);
    thesisRepository.save(project);
  }
}
