package pt.ul.fc.css.thesisman.business.entities.thesis;

import jakarta.persistence.*;
import pt.ul.fc.css.thesisman.business.entities.defense.ThesisDefense;
import pt.ul.fc.css.thesisman.business.entities.theme.DissertationTheme;
import pt.ul.fc.css.thesisman.business.entities.user.Student;

/**
 * Represents the dissertation that a student will have. Dissertation is mapped to a table named
 * 'dissertacao'. Dissertation is mapped to a table named 'dissertacao'.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "tese_dissertacao")
@PrimaryKeyJoinColumn(name = "tese_id")
public final class Dissertation extends Thesis {

  /** The dissertation's theme. */
  @OneToOne
  @JoinColumn(name = "tema_id")
  private DissertationTheme theme;

  /**
   * Constructs a new Dissertation.
   *
   * @param student the dissertation's student
   * @param theme the dissertation's defense
   * @param thesisDefense the dissertation's defense
   */
  public Dissertation(Student student, DissertationTheme theme, ThesisDefense thesisDefense) {
    super(student, thesisDefense, theme.getInternal());
    this.theme = theme;
  }

  /** No-arg constructor */
  protected Dissertation() {}

  /**
   * Sets the theme of this thesis.
   *
   * @param theme this thesis's theme
   */
  public void setTheme(DissertationTheme theme) {
    this.theme = theme;
  }

  /**
   * Returns the theme of this dissertation.
   *
   * @return the class theme
   */
  public DissertationTheme getTheme() {
    return theme;
  }

  /**
   * Returns a string representation of the dissertation.
   *
   * @return a string representation of the dissertation
   */
  @Override
  public String toString() {
    return "Dissertacao["
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
        + "defesaFinal="
        + getThesisDefense()
        + ']';
  }
}
