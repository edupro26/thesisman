package pt.ul.fc.css.thesisman.business.entities.user;

import jakarta.persistence.*;
import pt.ul.fc.css.thesisman.business.dtos.user.StudentDTO;
import pt.ul.fc.css.thesisman.business.entities.MastersDegree;

/**
 * Represents a student
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "utilizador_aluno")
public final class Student extends User {

  @ManyToOne
  @JoinColumn(name = "mestrado_id")
  private MastersDegree masters;

  @Column(name = "media", nullable = false)
  private Double average;

  /**
   * Constructs a new Student.
   *
   * @param name the name of this student
   * @param email the email of this student
   * @param password the password of this student
   * @param masters the name of the master’s degree of this student
   * @param average the average of this student
   */
  public Student(String name, String email, String password, MastersDegree masters, Double average) {
    super(name, email, password);
    this.masters = masters;
    this.average = average;
  }

  /** No-arg constructor */
  protected Student() {}

  /**
   * Sets the name of the master's degree of this student
   *
   * @param masters the name of the master's degree of this student
   */
  public void setMasters(MastersDegree masters) {
    this.masters = masters;
  }

  /**
   * Sets the average of this student
   *
   * @param average the average of this student
   */
  public void setAverage(Double average) {
    this.average = average;
  }

  /**
   * Returns the name of the master's degree of this student
   *
   * @return the name of the master's degree of this student
   */
  public MastersDegree getMasters() {
    return masters;
  }

  /**
   * Returns the average of this student
   *
   * @return the average of this student
   */
  public Double getAverage() {
    return average;
  }

  /**
   * Returns a string representation of this user
   *
   * @return a string representation of this user
   */
  @Override
  public String toString() {
    return "Aluno["
        + "id="
        + getId()
        + ", "
        + "nome="
        + getName()
        + ", "
        + "email="
        + getEmail()
        + ", "
        + "palavra-passe="
        + getPassword()
        + ", "
        + "mestrado="
        + masters
        + ", "
        + "media="
        + average
        + ']';
  }

  public StudentDTO toDTO() {
    StudentDTO dto = new StudentDTO();
    dto.setId(getId());
    dto.setName(getName());
    dto.setEmail(getEmail());
    dto.setMasters(getMasters().toDTO());
    dto.setAverage(getAverage());
    return dto;
  }
}
