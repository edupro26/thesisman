package pt.ul.fc.css.thesisman.business.dtos.theme;

import java.util.List;
import pt.ul.fc.css.thesisman.business.dtos.user.StudentDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.TeacherDTO;

public class ThemeApplicationDTO {

  private Long id;
  private StudentDTO student;
  private TeacherDTO admin;
  private List<ThemeDTO> themesList;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public StudentDTO getStudent() {
    return student;
  }

  public void setStudent(StudentDTO student) {
    this.student = student;
  }

  public TeacherDTO getAdmin() {
    return admin;
  }

  public void setAdmin(TeacherDTO admin) {
    this.admin = admin;
  }

  public List<ThemeDTO> getThemesList() {
    return themesList;
  }

  public void setThemesList(List<ThemeDTO> themesList) {
    this.themesList = themesList;
  }
}
