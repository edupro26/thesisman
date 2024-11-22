package pt.ul.fc.css.thesisman.business.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeApplicationDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeDTO;
import pt.ul.fc.css.thesisman.business.entities.theme.Theme;
import pt.ul.fc.css.thesisman.business.entities.user.Student;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;

/**
 * Represents an application. ThemeApplication is mapped to a table named 'candidatura'.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "candidatura")
public class ThemeApplication {

  /** The application's id. */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  /** The student that filled the application. */
  @OneToOne
  @JoinColumn(name = "aluno_id")
  private Student student;

  /** The teacher that will administrate the application. */
  @ManyToOne
  @JoinColumn(name = "administrador")
  private Teacher admin;

  /** The themes chosen for the application. */
  @ManyToMany
  @JoinTable(
      name = "candidatura_tema",
      joinColumns = @JoinColumn(name = "candidatura_id"),
      inverseJoinColumns = @JoinColumn(name = "tema_id"))
  private List<Theme> themes;

  /**
   * Constructs a new ThemeApplication.
   *
   * @param student the student that filled the application
   * @param admin the teacher that will administrate the application
   * @param themes the themes chosen for the application
   */
  public ThemeApplication(Student student, Teacher admin, List<Theme> themes) {
    this.student = student;
    this.admin = admin;
    this.themes = themes;
  }

  /** No-arg constructor */
  protected ThemeApplication() {}

  /**
   * Sets the application's id.
   *
   * @param id the application's id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Sets the student that filled the application.
   *
   * @param student the student that filled the application
   */
  public void setStudent(Student student) {
    this.student = student;
  }

  /**
   * Sets the teacher that will administrate the application.
   *
   * @param admin the teacher that will administrate the application
   */
  public void setAdmin(Teacher admin) {
    this.admin = admin;
  }

  /**
   * Sets the themes chosen for the application.
   *
   * @param themes the themes chosen for the application
   */
  public void setThemes(List<Theme> themes) {
    this.themes = themes;
  }

  /**
   * Gets the application's id.
   *
   * @return the application's id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the student that filled the application.
   *
   * @return the student that filled the application
   */
  public Student getStudent() {
    return student;
  }

  /**
   * Gets the teacher that will administrate the application.
   *
   * @return the teacher that will administrate the application
   */
  public Teacher getAdmin() {
    return admin;
  }

  /**
   * Gets the themes chosen for the application.
   *
   * @return the themes chosen for the application
   */
  public List<Theme> getThemes() {
    return themes;
  }

  /**
   * Returns a string representation of the application.
   *
   * @return a string representation of the application
   */
  @Override
  public String toString() {
    return "Candidatura["
        + "id="
        + id
        + ", "
        + "aluno="
        + student
        + ", "
        + "administrador="
        + admin
        + ", "
        + "temas="
        + themes
        + ']';
  }

  public ThemeApplicationDTO toDTO() {
    List<ThemeDTO> themeDTOList = new ArrayList<>();
    for (Theme t : themes) {
      themeDTOList.add(t.toDTO());
    }
    ThemeApplicationDTO dto = new ThemeApplicationDTO();
    dto.setId(id);
    dto.setStudent(student.toDTO());
    dto.setAdmin(admin.toDTO());
    dto.setThemesList(themeDTOList);
    return dto;
  }
}
