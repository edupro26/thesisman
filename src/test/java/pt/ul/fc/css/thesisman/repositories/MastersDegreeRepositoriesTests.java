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
import pt.ul.fc.css.thesisman.business.entities.theme.Theme;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;
import pt.ul.fc.css.thesisman.business.repositories.MastersDegreeRepository;
import pt.ul.fc.css.thesisman.business.repositories.theme.ThemeRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.TeacherRepository;

@DataJpaTest
public class MastersDegreeRepositoriesTests {

  @Autowired private MastersDegreeRepository mastersDegreeRepository;

  @Autowired private TeacherRepository teacherRepository;

  @Autowired private ThemeRepository themeRepository;

  @BeforeEach
  void setup() {
    Teacher teacher1 =
        teacherRepository.save(
            new Teacher("Professor1", "professor1@ciencias.ulisboa.pt", "password"));
    Teacher teacher2 =
        teacherRepository.save(
            new Teacher("Professor2", "professor2@ciencias.ulisboa.pt", "password"));

    mastersDegreeRepository.save(new MastersDegree("Mestrado1", teacher1));
    MastersDegree masters2 = mastersDegreeRepository.save(new MastersDegree("Mestrado2", teacher2));

    List<MastersDegree> list = new ArrayList<>();
    list.add(masters2);
    themeRepository.save(
        new DissertationTheme("Tema1", "", 200, list, new SchoolYear("2023/2024"), teacher1));
  }

  @Test
  void shouldFindByName() {
    String name = "Mestrado1";
    Optional<MastersDegree> degree = mastersDegreeRepository.findByName(name);
    assertTrue(degree.isPresent());
  }

  @Test
  void shouldFindByCoordinatorName() {
    String name = "Professor1";
    Optional<MastersDegree> degree = mastersDegreeRepository.findByCoordinatorName(name);
    assertTrue(degree.isPresent());
  }

  @Test
  void shouldReturnThemes() {
    String name = "Mestrado2";
    List<Theme> list = mastersDegreeRepository.findThemesByMastersName(name);
    assertFalse(list.isEmpty());
  }

  @Test
  void shouldNotFindByName() {
    String name = "Mestrado3";
    Optional<MastersDegree> degree = mastersDegreeRepository.findByName(name);
    assertTrue(degree.isEmpty());
  }

  @Test
  void shouldNotFindByCoordinatorName() {
    String name = "Professor3";
    Optional<MastersDegree> degree = mastersDegreeRepository.findByCoordinatorName(name);
    assertFalse(degree.isPresent());
  }

  @Test
  void shouldReturnEmptyThemes() {
    String name = "Mestrado1";
    List<Theme> list = mastersDegreeRepository.findThemesByMastersName(name);
    assertTrue(list.isEmpty());
  }
}
