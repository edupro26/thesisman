package pt.ul.fc.css.thesisman.business.handlers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.StudentDTO;
import pt.ul.fc.css.thesisman.business.entities.MastersDegree;
import pt.ul.fc.css.thesisman.business.entities.SchoolYear;
import pt.ul.fc.css.thesisman.business.services.MastersDegreeService;
import pt.ul.fc.css.thesisman.business.services.ProposalService;
import pt.ul.fc.css.thesisman.business.services.ThemeService;
import pt.ul.fc.css.thesisman.business.services.UserService;

@Component
public class ThemeAssignHandler {

  private final ThemeService themeService;

  private final UserService userService;

  private final ProposalService proposalService;

  private final MastersDegreeService mastersDegreeService;

  private final SessionHandler sessionHandler;

  @Autowired
  public ThemeAssignHandler(
      ThemeService themeService,
      UserService userService,
      ProposalService proposalService,
      MastersDegreeService mastersDegreeService,
      SessionHandler sessionHandler) {
    this.themeService = themeService;
    this.userService = userService;
    this.proposalService = proposalService;
    this.mastersDegreeService = mastersDegreeService;
    this.sessionHandler = sessionHandler;
  }

  public List<ThemeDTO> getNotAssignedThemes() {
    Optional<MastersDegree> masters =
        mastersDegreeService.findByCoordinatorName(sessionHandler.getCurrentUser().getName());

    if (masters.isEmpty()) {
      new ArrayList<>();
    }

    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    SchoolYear schoolYear = new SchoolYear(year + "/" + (year + 1));

    return themeService.findByMastersAndSchoolYearWithoutProposals(masters.get(), schoolYear);
  }

  public List<StudentDTO> getApplicantsForTheme(Long themeId) {
    return userService.findStudentsNotAssociatedWithAnyProposalAndWithTheme(themeId);
  }

  public void assignThemeToStudent(Long studentId, Long themeId) {
    Optional<ThemeDTO> theme = themeService.findById(themeId);

    if (theme.isEmpty()) {
      throw new IllegalArgumentException("Theme not found");
    }

    Optional<MastersDegree> masters =
        mastersDegreeService.findByCoordinatorName(sessionHandler.getCurrentUser().getName());

    if (masters.isEmpty()) {
      throw new IllegalArgumentException("The user is not a coordinator of any masters degree");
    }

    if (!theme.get().getMasters().contains(masters.get().toDTO())) {
      throw new IllegalArgumentException(
          "The theme is not associated with the coordinator's masters degree");
    }

    proposalService.createProposal(studentId, themeId);
  }
}
