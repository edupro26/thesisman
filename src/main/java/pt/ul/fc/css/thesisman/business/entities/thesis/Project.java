package pt.ul.fc.css.thesisman.business.entities.thesis;

import jakarta.persistence.*;
import pt.ul.fc.css.thesisman.business.entities.defense.ThesisDefense;
import pt.ul.fc.css.thesisman.business.entities.theme.ProjectTheme;
import pt.ul.fc.css.thesisman.business.entities.user.CompanyUser;
import pt.ul.fc.css.thesisman.business.entities.user.Student;

/**
 * Represents the project that a student will have. Project is mapped to a table named 'projeto'.
 * Project is mapped to a table named 'projeto'.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "tese_projeto")
@PrimaryKeyJoinColumn(name = "tese_id")
public final class Project extends Thesis {

  /** The project's theme. */
  @OneToOne
  @JoinColumn(name = "tema_id")
  private ProjectTheme theme;

  /** The external advisor of this project. */
  @ManyToOne
  @JoinColumn(name = "orientador_externo")
  private CompanyUser external;

  /**
   * Constructs a new Project.
   *
   * @param student the project's student
   * @param theme the project's theme
   * @param thesisDefense the project's defense
   */
  public Project(Student student, ProjectTheme theme, ThesisDefense thesisDefense) {
    super(student, thesisDefense, theme.getInternal());
    this.theme = theme;
    this.external = theme.getExternal();
  }

  /** No-arg constructor */
  protected Project() {}

  /**
   * Sets the theme of this thesis.
   *
   * @param theme this thesis's theme
   */
  public void setTheme(ProjectTheme theme) {
    this.theme = theme;
  }

  /**
   * Sets the theme of this thesis.
   *
   * @param external this project's external advisor
   */
  public void setExternal(CompanyUser external) {
    this.external = external;
  }

  /**
   * Returns this project's theme.
   *
   * @return the project's theme
   */
  public ProjectTheme getTheme() {
    return theme;
  }

  /**
   * Returns this project's external advisor.
   *
   * @return the project's external advisor
   */
  public CompanyUser getExternal() {
    return external;
  }

  /**
   * Returns a string representation of the project.
   *
   * @return a string representation of the project
   */
  @Override
  public String toString() {
    return "Projeto["
        + "id="
        + getId()
        + ", "
        + "aluno"
        + getStudent()
        + ", "
        + "tema="
        + theme
        + ", "
        + "orientadorInterno="
        + getInternal()
        + ", "
        + "orientadorExterno="
        + external
        + ", "
        + "defesaFinal="
        + getThesisDefense()
        + ']';
  }
}
