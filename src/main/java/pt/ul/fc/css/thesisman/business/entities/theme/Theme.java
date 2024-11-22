package pt.ul.fc.css.thesisman.business.entities.theme;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import pt.ul.fc.css.thesisman.business.dtos.masters.MastersDegreeDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeDTO;
import pt.ul.fc.css.thesisman.business.entities.MastersDegree;
import pt.ul.fc.css.thesisman.business.entities.SchoolYear;

/**
 * Represents a theme. Theme is mapped to a table named 'tema'. Can be a theme for a dissertation or
 * a project. Theme is mapped to a table named 'tema'.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "tema")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Theme {

  /** The theme's id. */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  /** The theme's title. */
  @Column(name = "titulo", nullable = false, unique = true)
  private String title;

  /** The theme's description. */
  @Column(name = "descricao", nullable = false)
  private String description;

  /** The theme's remuneration per month. */
  @Column(name = "remuneracao_mensal", nullable = false)
  private double remuneration;

  /** The theme's compatible master's degrees. */
  @ManyToMany
  @JoinTable(
      name = "tema_mestrado",
      joinColumns = @JoinColumn(name = "tema_id"),
      inverseJoinColumns = @JoinColumn(name = "mestrado_id"))
  private List<MastersDegree> masters;

  @Embedded private SchoolYear schoolYear;

  /**
   * Constructs a new Theme.
   *
   * @param title the theme's title
   * @param description the theme's description
   * @param masters the theme's masters
   * @param remuneration the theme's remuneration
   */
  public Theme(
      String title,
      String description,
      double remuneration,
      List<MastersDegree> masters,
      SchoolYear schoolYear) {
    this.title = title;
    this.description = description;
    this.remuneration = remuneration;
    this.masters = masters;
    this.schoolYear = schoolYear;
  }

  /** No-arg constructor */
  protected Theme() {}

  /**
   * Sets the theme's id.
   *
   * @param id the theme's id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Sets the theme's title.
   *
   * @param title the theme's title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Sets the theme's description.
   *
   * @param description the theme's description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Sets the theme's masters.
   *
   * @param masters the theme's masters
   */
  public void setMasters(List<MastersDegree> masters) {
    this.masters = masters;
  }

  /**
   * Sets the theme's remuneration.
   *
   * @param remuneration the theme's remuneration
   */
  public void setRemuneration(double remuneration) {
    this.remuneration = remuneration;
  }

  public void setSchoolYear(SchoolYear schoolYear) {
    this.schoolYear = schoolYear;
  }

  /**
   * Returns the theme's id.
   *
   * @return the theme's id
   */
  public Long getId() {
    return id;
  }

  /**
   * Returns the theme's title.
   *
   * @return the theme's title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the theme's description.
   *
   * @return the theme's description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the theme's masters.
   *
   * @return the theme's masters
   */
  public List<MastersDegree> getMasters() {
    return masters;
  }

  /**
   * Returns the theme's remuneration.
   *
   * @return the theme's remuneration
   */
  public double getRemuneration() {
    return remuneration;
  }

  public SchoolYear getSchoolYear() {
    return schoolYear;
  }

  public ThemeDTO toDTO() {
    ThemeDTO dto = new ThemeDTO();
    List<MastersDegreeDTO> masters = new ArrayList<>();
    for (MastersDegree m : getMasters()) masters.add(m.toDTO());
    dto.setId(getId());
    dto.setTitle(title);
    dto.setDescription(description);
    dto.setRemuneration(remuneration);
    dto.setMasters(masters);
    dto.setSchoolYear(schoolYear);
    return dto;
  }
}
