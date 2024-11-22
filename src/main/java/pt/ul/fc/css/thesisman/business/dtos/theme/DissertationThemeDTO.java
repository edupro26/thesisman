package pt.ul.fc.css.thesisman.business.dtos.theme;
import pt.ul.fc.css.thesisman.business.dtos.user.TeacherDTO;

public class DissertationThemeDTO extends ThemeDTO {

  private TeacherDTO internal;

  public TeacherDTO getInternal() {
    return internal;
  }

  public void setInternal(TeacherDTO internal) {
    this.internal = internal;
  }
}
