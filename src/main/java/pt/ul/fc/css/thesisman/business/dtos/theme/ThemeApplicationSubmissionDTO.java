package pt.ul.fc.css.thesisman.business.dtos.theme;

import java.util.List;

public class ThemeApplicationSubmissionDTO {

  private Long studentId;
  private List<ThemeDTO> themesList;

  public Long getStudent() {
    return studentId;
  }

  public void setStudent(Long studentId) {
    this.studentId = studentId;
  }

  public List<ThemeDTO> getThemes() {
    return themesList;
  }

  public void setThemes(List<ThemeDTO> themesList) {
    this.themesList = themesList;
  }
}
