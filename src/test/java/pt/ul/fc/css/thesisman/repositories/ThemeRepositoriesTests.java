package pt.ul.fc.css.thesisman.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pt.ul.fc.css.thesisman.business.entities.MastersDegree;
import pt.ul.fc.css.thesisman.business.entities.SchoolYear;
import pt.ul.fc.css.thesisman.business.entities.theme.DissertationTheme;
import pt.ul.fc.css.thesisman.business.entities.theme.ProjectTheme;
import pt.ul.fc.css.thesisman.business.entities.theme.Theme;
import pt.ul.fc.css.thesisman.business.entities.user.CompanyUser;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;
import pt.ul.fc.css.thesisman.business.repositories.MastersDegreeRepository;
import pt.ul.fc.css.thesisman.business.repositories.theme.DissertationThemeRepository;
import pt.ul.fc.css.thesisman.business.repositories.theme.ProjectThemeRepository;
import pt.ul.fc.css.thesisman.business.repositories.theme.ThemeRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.UserRepository;

@DataJpaTest
public class ThemeRepositoriesTests {

  @Autowired private ThemeRepository themeRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private MastersDegreeRepository mastersDegreeRepository;

  @Autowired private DissertationThemeRepository dissertationThemeRepository;

  @Autowired private ProjectThemeRepository projectThemeRepository;

  @BeforeEach
  void setup() {
    Teacher teacher1 =
        userRepository.save(
            new Teacher("Professor1", "professor1@ciencias.ulisboa.pt", "password"));
    Teacher teacher2 =
        userRepository.save(
            new Teacher("Professor2", "professor2@ciencias.ulisboa.pt", "password"));
    CompanyUser companyUser =
        userRepository.save(
            new CompanyUser("Empresarial1", "empresarial1@empresa.pt", "password", "345135162"));

    MastersDegree masters1 = mastersDegreeRepository.save(new MastersDegree("Mestrado1", teacher1));
    MastersDegree masters2 = mastersDegreeRepository.save(new MastersDegree("Mestrado2", teacher2));

    List<MastersDegree> list = new ArrayList<>();
    list.add(masters1);
    themeRepository.save(
        new DissertationTheme("Tema1", "", 200, list, new SchoolYear("2023/2024"), teacher1));

    list.add(masters2);
    themeRepository.save(
        new DissertationTheme("Tema2", "", 300, list, new SchoolYear("2023/2024"), teacher2));

    themeRepository.save(
        new ProjectTheme(
            "Tema3", "", 300, list, new SchoolYear("2023/2024"), teacher2, companyUser));
  }

  @Test
  void shouldFindThemeByTitleAndYear() {
    String title = "Tema1";
    SchoolYear year = new SchoolYear("2023/2024");
    Optional<Theme> list = themeRepository.findByTitleAndYear(title, year);
    assertTrue(list.isPresent());
  }

  @Test
  void shouldFindThemesByMastersAndYear() {
    String masters = "Mestrado1";
    String year = "2023/2024";
    List<Theme> list = themeRepository.findByMastersAndYear(masters, year);
    assertFalse(list.isEmpty());
  }

  @Test
  void shouldFindThemesByInternalEmail() {
    String email1 = "professor1@ciencias.ulisboa.pt";
    List<DissertationTheme> list1 = dissertationThemeRepository.findByInternalEmail(email1);
    assertFalse(list1.isEmpty());

    String email2 = "professor2@ciencias.ulisboa.pt";
    List<ProjectTheme> list2 = projectThemeRepository.findByInternalEmail(email2);
    assertFalse(list2.isEmpty());
  }

  @Test
  void shouldFindThemesByExternalEmail() {
    String email = "empresarial1@empresa.pt";
    List<ProjectTheme> list = projectThemeRepository.findByExternalEmail(email);
    assertFalse(list.isEmpty());
  }
}
