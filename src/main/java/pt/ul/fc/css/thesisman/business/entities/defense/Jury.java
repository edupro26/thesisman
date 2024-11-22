package pt.ul.fc.css.thesisman.business.entities.defense;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;

/**
 * Represents a jury that will evaluate a defense. Maps to a table named 'juri'.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "juri")
public class Jury {

  /** The jury's id. */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  /** The internal advisor of the jury. */
  @ManyToOne
  @JoinColumn(name = "orientador_interno_id")
  private Teacher internalAdvisor;

  /** The arguer of the jury. */
  @ManyToOne
  @JoinColumn(name = "arguente_id")
  private Teacher arguer;

  /** The president of the jury. */
  @ManyToOne
  @JoinColumn(name = "presidente_id")
  private Teacher president;

  /**
   * Constructs a new Jury.
   *
   * @param internalAdvisor the internal advisor of the jury
   * @param arguer the arguer of the jury
   * @param president the president of the jury
   */
  public Jury(Teacher internalAdvisor, Teacher arguer, Teacher president) {
    this.internalAdvisor = internalAdvisor;
    this.arguer = arguer;
    this.president = president;
  }

  /** No-arg constructor */
  protected Jury() {}

  /**
   * Gets the jury's id.
   *
   * @return the jury's id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the internal advisor of the jury.
   *
   * @return the internal advisor of the jury
   */
  public Teacher getInternalAdvisor() {
    return internalAdvisor;
  }

  /**
   * Gets the arguer of the jury.
   *
   * @return the arguer of the jury
   */
  public Teacher getArguer() {
    return arguer;
  }

  /**
   * Gets the president of the jury.
   *
   * @return the president of the jury
   */
  public Teacher getPresident() {
    return president;
  }

  /**
   * Sets the jury's internal advisor.
   *
   * @param internalAdvisor the jury's internal advisor
   */
  public void setInternalAdvisor(Teacher internalAdvisor) {
    this.internalAdvisor = internalAdvisor;
  }

  /**
   * Sets the jury's arguer.
   *
   * @param arguer the jury's arguer
   */
  public void setArguer(Teacher arguer) {
    this.arguer = arguer;
  }

  /**
   * Sets the jury's president.
   *
   * @param president the jury's president
   */
  public void setPresident(Teacher president) {
    this.president = president;
  }

  /**
   * Returns a string representation of the jury.
   *
   * @return a string representation of the jury
   */
  @Override
  public String toString() {
    return "Juri["
        + "id="
        + id
        + ", "
        + "orientadorInterno="
        + internalAdvisor
        + ", "
        + "arguente="
        + arguer
        + ", "
        + "presidente="
        + president
        + ']';
  }
}
