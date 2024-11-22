package pt.ul.fc.css.thesisman.business.entities.theme;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.List;
import pt.ul.fc.css.thesisman.business.dtos.theme.DissertationThemeDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeDTO;
import pt.ul.fc.css.thesisman.business.entities.MastersDegree;
import pt.ul.fc.css.thesisman.business.entities.SchoolYear;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;

/**
 * Represents a dissertation theme. Dissertation theme is mapped to a table named 'tema'.
 * Dissertation theme is mapped to a table named 'tema'.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "tema_dissertacao")
@PrimaryKeyJoinColumn(name = "tema_id")
public final class DissertationTheme extends Theme {

  /** The internal teacher specified to advise dissertations of this theme. */
  @ManyToOne
  @JoinColumn(name = "orientador_interno")
  private Teacher internal;

  /**
   * Constructs a new DissertationTheme.
   *
   * @param title the dissertation theme's title
   * @param description the dissertation theme's description
   * @param masters the dissertation theme's compatible master's degrees
   * @param remuneration the dissertation theme's remuneration
   * @param internal the internal teacher specified to advise dissertations of this theme
   */
  public DissertationTheme(
      String title,
      String description,
      double remuneration,
      List<MastersDegree> masters,
      SchoolYear schoolYear,
      Teacher internal) {
    super(title, description, remuneration, masters, schoolYear);
    this.internal = internal;
  }

  public DissertationTheme(Teacher internal) {
    this.internal = internal;
  }

  /** No-arg constructor */
  protected DissertationTheme() {
    super();
  }

  /**
   * Sets the advisor teacher for this dissertation theme.
   *
   * @param internal the advisor teacher for this dissertation theme
   */
  public void setInternal(Teacher internal) {
    this.internal = internal;
  }

  /**
   * Gets the advisor teacher for this dissertation theme.
   *
   * @return the advisor teacher.
   */
  public Teacher getInternal() {
    return internal;
  }

  /**
   * Returns a string representation of the dissertation theme.
   *
   * @return a string representation of the dissertation theme
   */
  @Override
  public String toString() {
    return "TemaDissertacao["
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
        + "oritentadorInterno"
        + internal
        + ']';
  }

  public DissertationThemeDTO toDTO() {
    ThemeDTO superDTO = super.toDTO();
    DissertationThemeDTO dto = new DissertationThemeDTO();
    dto.setId(superDTO.getId());
    dto.setTitle(superDTO.getTitle());
    dto.setDescription(superDTO.getDescription());
    dto.setRemuneration(superDTO.getRemuneration());
    dto.setMasters(superDTO.getMasters());
    dto.setSchoolYear(superDTO.getSchoolYear());
    dto.setInternal(internal.toDTO());
    return dto;
  }
}
