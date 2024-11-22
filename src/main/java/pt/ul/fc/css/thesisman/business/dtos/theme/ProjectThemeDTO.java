package pt.ul.fc.css.thesisman.business.dtos.theme;
import pt.ul.fc.css.thesisman.business.dtos.user.CompanyUserDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.TeacherDTO;

public class ProjectThemeDTO extends ThemeDTO {

  private TeacherDTO internal;
  private CompanyUserDTO external;

  public TeacherDTO getInternal() {
    return internal;
  }

  public void setInternal(TeacherDTO internal) {
    this.internal = internal;
  }

  public CompanyUserDTO getExternal() {
    return external;
  }

  public void setExternal(CompanyUserDTO external) {
    this.external = external;
  }
}
