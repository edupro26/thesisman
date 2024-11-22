package pt.ul.fc.css.thesisman.business.entities.theme;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import pt.ul.fc.css.thesisman.business.dtos.theme.ProjectThemeDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeDTO;
import pt.ul.fc.css.thesisman.business.entities.MastersDegree;
import pt.ul.fc.css.thesisman.business.entities.SchoolYear;
import pt.ul.fc.css.thesisman.business.entities.user.CompanyUser;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;

/**
 * Represents a project theme. Project theme is mapped to a table named 'tema'. Project theme is
 * mapped to a table named 'tema'.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "tema_projeto")
@PrimaryKeyJoinColumn(name = "tema_id")
public final class ProjectTheme extends Theme {

  /** The internal teacher specified to advice projects of this theme. */
  @ManyToOne
  @JoinColumn(name = "orientador_interno")
  private Teacher internal;

  /** The external company that suggested this theme and will advise projects of this theme. */
  @ManyToOne
  @JoinColumn(name = "orientador_externo")
  private CompanyUser external;

  /**
   * Constructs a new ProjectTheme.
   *
   * @param title the project theme's title
   * @param description the project theme's description
   * @param masters the project theme's compatible master's degrees
   * @param remuneration the project theme's remuneration
   * @param internal the internal teacher specified to advice projects of this theme
   * @param external the external company that suggested this theme and will advise projects of this
   *     theme
   */
  public ProjectTheme(
      String title,
      String description,
      double remuneration,
      List<MastersDegree> masters,
      SchoolYear schoolYear,
      Teacher internal,
      CompanyUser external) {
    super(title, description, remuneration, masters, schoolYear);
    this.internal = internal;
    this.external = external;
  }

  public ProjectTheme(Teacher internal, CompanyUser external) {
    this.internal = internal;
    this.external = external;
  }

  /** No-arg constructor */
  protected ProjectTheme() {
    super();
  }

  /** Sets the advisor teacher for this project theme. */
  public void setInternal(Teacher internal) {
    this.internal = internal;
  }

  /** Sets the external company for this project theme. */
  public void setExternal(CompanyUser external) {
    this.external = external;
  }

  /**
   * Gets the advisor teacher for this project theme.
   *
   * @return the advisor teacher for this project theme
   */
  public Teacher getInternal() {
    return internal;
  }

  /**
   * Gets the external company for this project theme.
   *
   * @return the external company for this project theme
   */
  public CompanyUser getExternal() {
    return external;
  }

  /**
   * Returns a string representation of the project theme.
   *
   * @return a string representation of the project theme
   */
  @Override
  public String toString() {
    return "TemaProjeto["
        + "id="
        + getId()
        + ", "
        + "titulo="
        + getTitle()
        + ", "
        + "descricao"
        + getDescription()
        + ", "
        + "mestrados="
        + getMasters()
        + ", "
        + "remuneracao="
        + getRemuneration()
        + ", "
        + "orientadorInterno"
        + internal
        + ", "
        + "orientadorExterno"
        + external
        + ']';
  }

  public ProjectThemeDTO toDTO() {
    ThemeDTO superDTO = super.toDTO();
    ProjectThemeDTO dto = new ProjectThemeDTO();
    dto.setId(superDTO.getId());
    dto.setTitle(superDTO.getTitle());
    dto.setDescription(superDTO.getDescription());
    dto.setRemuneration(superDTO.getRemuneration());
    dto.setMasters(superDTO.getMasters());
    dto.setSchoolYear(superDTO.getSchoolYear());
    dto.setInternal(internal.toDTO());
    dto.setExternal(external.toDTO());
    return dto;
  }
}
