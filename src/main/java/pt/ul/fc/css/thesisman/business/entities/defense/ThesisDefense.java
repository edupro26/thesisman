package pt.ul.fc.css.thesisman.business.entities.defense;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import pt.ul.fc.css.thesisman.business.dtos.DefenseDTO;
import pt.ul.fc.css.thesisman.business.entities.defense.location.Reservation;
import pt.ul.fc.css.thesisman.business.entities.thesis.Thesis;

/**
 * Represents a thesis final defense. It is submitted in June. The thesis defense lasts 90 minutes.
 * Maps to a table named 'defesa_tese'.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "defesa_tese")
@PrimaryKeyJoinColumn(name = "defesa_id")
public final class ThesisDefense extends Defense {

  /** The jury that will evaluate the defense. */
  @OneToOne
  @JoinColumn(name = "juri_id")
  private Jury jury;

  /** The thesis final defense's duration. */
  @Column(name = "duracao", nullable = false)
  private int duration;

  /** The thesis that will be defended. */
  @OneToOne
  @JoinColumn(name = "tese_id")
  private Thesis thesis;

  /**
   * Constructs a new ThesisDefense.
   *
   * @param reservation where the defense will take place
   * @param jury the jury that will evaluate the defense
   * @param grade the grade given to the defense
   */
  public ThesisDefense(Reservation reservation, Jury jury, float grade, Thesis thesis) {
    super(reservation, grade);
    this.jury = jury;
    this.thesis = thesis;
    this.duration = 90;
  }

  /** No-arg constructor */
  public ThesisDefense() {}

  /**
   * Sets the jury that will evaluate the defense.
   *
   * @param jury the jury that will evaluate the defense
   */
  public void setJury(Jury jury) {
    this.jury = jury;
  }

  /**
   * Sets the thesis final defense's duration.
   *
   * @param duration the thesis final defense's duration
   */
  public void setDuration(int duration) {
    this.duration = duration;
  }

  /**
   * Sets the thesis that will be defended.
   *
   * @param thesis the thesis that will be defended
   */
  public void setThesis(Thesis thesis) {
    this.thesis = thesis;
  }

  /**
   * Gets the jury that will evaluate the defense.
   *
   * @return the jury that will evaluate the defense
   */
  public Jury getJury() {
    return jury;
  }

  /**
   * Gets the thesis final defense's duration.
   *
   * @return the thesis final defense's duration
   */
  public int getDuration() {
    return duration;
  }

  /**
   * Gets the thesis that will be defended.
   *
   * @return the thesis that will be defended
   */
  public Thesis getThesis() {
    return thesis;
  }

  /**
   * Returns a string representation of the thesis final defense.
   *
   * @return a string representation of the thesis final defense
   */
  @Override
  public String toString() {
    return "DefesaTese["
        + "id="
        + getId()
        + ", "
        + "duracao="
        + duration
        + ", "
        + "local="
        + getReservation()
        + ", "
        + "juri="
        + getJury()
        + ", "
        + "nota="
        + getGrade()
        + ']';
  }

  public DefenseDTO toDTO() {
    DefenseDTO defenseDTO = new DefenseDTO();
    defenseDTO.setId(getId());
    defenseDTO.setDate(getReservation().getDate());
    defenseDTO.setDuration(duration);
    defenseDTO.setThemeTitle(getThesis().getTheme().getTitle());
    defenseDTO.setStudent(thesis.getStudent().toDTO());
    defenseDTO.setType("Tese");
    defenseDTO.setGrade(getGrade());
    defenseDTO.setReservation(getReservation());
    defenseDTO.setTheme(thesis.getTheme().toDTO());
    return defenseDTO;
  }
}
