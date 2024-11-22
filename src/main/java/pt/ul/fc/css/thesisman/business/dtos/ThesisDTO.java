package pt.ul.fc.css.thesisman.business.dtos;

import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.StudentDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.TeacherDTO;

public class ThesisDTO {
  private Long id;

  private StudentDTO student;

  private TeacherDTO internal;

  private DefenseDTO thesisDefense;

  private ThemeDTO theme;

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

  public TeacherDTO getInternal() {
    return internal;
  }

  public void setInternal(TeacherDTO internal) {
    this.internal = internal;
  }

  public DefenseDTO getThesisDefense() {
    return thesisDefense;
  }

  public void setThesisDefense(DefenseDTO thesisDefense) {
    this.thesisDefense = thesisDefense;
  }

  public ThemeDTO getTheme() {
    return theme;
  }

  public void setTheme(ThemeDTO theme) {
    this.theme = theme;
  }
}
