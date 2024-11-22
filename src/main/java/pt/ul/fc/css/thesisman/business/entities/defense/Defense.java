package pt.ul.fc.css.thesisman.business.entities.defense;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import pt.ul.fc.css.thesisman.business.entities.defense.location.Reservation;

/**
 * Represents a defense. Can be a proposal defense or the final defense of a thesis.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "defesa")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Defense {

  /** The defense's id. */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  /** Where the defense will take place. */
  @Embedded private Reservation reservation;

  /** The grade given to the defense. */
  @Column(name = "nota")
  private Float grade;

  /**
   * Constructs a new Defense.
   *
   * @param reservation where and when the defence will take place
   * @param grade the grade given to the defense
   */
  protected Defense(Reservation reservation, Float grade) {
    this.reservation = reservation;
    this.grade = grade;
  }

  /** No-arg constructor */
  protected Defense() {}

  /**
   * Sets the id of this defense
   *
   * @param id the id of this defense
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Sets the reservation where the defense will take place.
   *
   * @param reservation where the defense will take place
   */
  public void setReservation(Reservation reservation) {
    this.reservation = reservation;
  }

  /**
   * Sets the grade given to the defense.
   *
   * @param grade the grade given to the defense
   */
  public void setGrade(Float grade) {
    this.grade = grade;
  }

  /**
   * Returns the id of this defense
   *
   * @return the id of this defense
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the location where the defense will take place.
   *
   * @return the location where the defense will take place
   */
  public Reservation getReservation() {
    return reservation;
  }

  /**
   * Gets the grade given to the defense.
   *
   * @return the grade given to the defense
   */
  public Float getGrade() {
    return grade;
  }
}
