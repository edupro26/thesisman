package pt.ul.fc.css.thesisman.business.handlers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeApplicationDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeApplicationSubmissionDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.StudentDTO;
import pt.ul.fc.css.thesisman.business.services.TApplicationService;
import pt.ul.fc.css.thesisman.business.services.ThemeService;
import pt.ul.fc.css.thesisman.business.services.UserService;

@Component
public class ThemeApplicationHandler {

  private final ThemeService themeService;
  private final TApplicationService themeApplicationService;
  private final UserService userService;

  @Autowired
  public ThemeApplicationHandler(
      ThemeService themeService,
      TApplicationService themeApplicationService,
      UserService userService) {
    this.themeService = themeService;
    this.themeApplicationService = themeApplicationService;
    this.userService = userService;
  }

  public List<ThemeDTO> findThemesByMastersAndYear(String masters, String year) {
    return themeService.findByMastersAndYear(masters, year);
  }

  public Optional<ThemeApplicationDTO> findByStudentId(Long id) {
    return themeApplicationService.findByStudentId(id);
  }

  public ThemeApplicationDTO submitThemeApplication(ThemeApplicationSubmissionDTO themeApplication)
      throws IllegalArgumentException {
    Optional<StudentDTO> student = userService.findStudentById(themeApplication.getStudent());
    if (student.isEmpty()) {
      throw new IllegalArgumentException("Student not found");
    }

    Optional<ThemeApplicationDTO> applicationDTO =
        themeApplicationService.findByStudentId(student.get().getId());
    applicationDTO.ifPresent(
        themeApplicationDTO -> themeApplicationService.deleteById(themeApplicationDTO.getId()));

    Optional<ThemeApplicationDTO> themeApplicationDTO =
        themeApplicationService.createThemeApplication(themeApplication);
    return themeApplicationDTO.orElse(null);
  }

  public void cancelThemeApplication(Long id) throws IllegalArgumentException {
    Optional<ThemeApplicationDTO> application = themeApplicationService.findByStudentId(id);
    if (application.isEmpty()) {
      throw new IllegalArgumentException("Theme application not found");
    }
    themeApplicationService.deleteById(application.get().getId());
  }
}
