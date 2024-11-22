package pt.ul.fc.css.thesisman.business.handlers;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.business.dtos.masters.MastersDegreeDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeSubmissionDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.TeacherDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.UserDTO;
import pt.ul.fc.css.thesisman.business.entities.SchoolYear;
import pt.ul.fc.css.thesisman.business.exceptions.EmptyFieldsException;
import pt.ul.fc.css.thesisman.business.exceptions.NegativeRemunerationException;
import pt.ul.fc.css.thesisman.business.exceptions.TitleNotAvailableException;
import pt.ul.fc.css.thesisman.business.services.MastersDegreeService;
import pt.ul.fc.css.thesisman.business.services.ThemeService;
import pt.ul.fc.css.thesisman.business.services.UserService;

@Component
public class ThemeSubmissionHandler {

  private final ThemeService themeService;
  private final UserService userService;
  private final MastersDegreeService mastersDegreeService;

  @Autowired
  public ThemeSubmissionHandler(
      ThemeService themeService,
      MastersDegreeService mastersDegreeService,
      UserService userService) {
    this.themeService = themeService;
    this.mastersDegreeService = mastersDegreeService;
    this.userService = userService;
  }

  public List<MastersDegreeDTO> getMastersDegrees() {
    return mastersDegreeService.findAll();
  }

  public List<TeacherDTO> getTeachers() {
    return userService.findAllTeachers();
  }

  public void submitTheme(ThemeSubmissionDTO submission, UserDTO submitter)
      throws TitleNotAvailableException, EmptyFieldsException, NegativeRemunerationException {
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    SchoolYear schoolYear = new SchoolYear(year + "/" + (year + 1));

    verifySubmission(submission); // Verify input from the user

    String title = submission.getTitle();
    Optional<ThemeDTO> theme = themeService.findByTitleAndYear(title, schoolYear);
    if (theme.isPresent()) {
      throw new TitleNotAvailableException();
    }
    if (submitter instanceof TeacherDTO) {
      themeService.saveDissertationTheme(submission, schoolYear, submitter);
    } else {
      themeService.saveProjectTheme(submission, schoolYear, submitter);
    }
  }

  private void verifySubmission(ThemeSubmissionDTO submission)
      throws EmptyFieldsException, NegativeRemunerationException {
    if (submission.getTitle().isEmpty()) {
      throw new EmptyFieldsException("Por favor preencha o título");
    } else if (submission.getDescription().isEmpty()) {
      throw new EmptyFieldsException("Por favor preencha a descrição");
    } else if (submission.getRemuneration() <= 0) {
      throw new NegativeRemunerationException();
    } else if (submission.getSelectedMasters() == null) {
      throw new EmptyFieldsException("Por favor selecione os mestrados compatíveis");
    }
  }
}
