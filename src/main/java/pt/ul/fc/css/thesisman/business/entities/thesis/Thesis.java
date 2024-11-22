package pt.ul.fc.css.thesisman.business.entities.thesis;

import jakarta.persistence.*;
import pt.ul.fc.css.thesisman.business.dtos.ThesisDTO;
import pt.ul.fc.css.thesisman.business.entities.defense.ThesisDefense;
import pt.ul.fc.css.thesisman.business.entities.theme.Theme;
import pt.ul.fc.css.thesisman.business.entities.user.Student;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;

/**
 * Represents a thesis with its default methods and attributes.This abstract class implements
 * methods for setting and getting a student and it's defense. Thesis is mapped to a table named
 * 'tese'.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "tese")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Thesis {
  /** The thesis's id. */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  /** The thesis's student. */
  @OneToOne
  @JoinColumn(name = "aluno_id")
  private Student student;

  /** The teacher related to this thesis. */
  @ManyToOne
  @JoinColumn(name = "orientador_interno")
  private Teacher internal;

  /** The thesis's defense. */
  @OneToOne
  @JoinColumn(name = "defesa_final_id")
  private ThesisDefense thesisDefense;

  /**
   * Constructs a new Thesis.
   *
   * @param student the thesis's student
   * @param thesisDefense the thesis's defense
   */
  protected Thesis(Student student, ThesisDefense thesisDefense, Teacher internal) {
    this.student = student;
    this.thesisDefense = thesisDefense;
    this.internal = internal;
  }

  /** No-arg constructor */
  protected Thesis() {}

  /**
   * Sets the ID of the thesis.
   *
   * @param id the unique identifier for the thesis
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Returns the ID of the thesis.
   *
   * @return the thesis's ID
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the student responsible for the thesis.
   *
   * @param student the student making the thesis
   */
  public void setStudent(Student student) {
    this.student = student;
  }

  /**
   * Sets the internal advisor of this thesis.
   *
   * @param internal this thesis's internal advisor
   */
  public void setInternal(Teacher internal) {
    this.internal = internal;
  }

  /**
   * Returns the student responsible for the thesis.
   *
   * @return the class student
   */
  public Student getStudent() {
    return this.student;
  }

  /**
   * Returns the internal advisor responsible for the thesis.
   *
   * @return internal advisor
   */
  public Teacher getInternal() {
    return internal;
  }

  /**
   * Sets the defense of this thesis.
   *
   * @param thesisDefense this thesis's defense
   */
  public void setDefense(ThesisDefense thesisDefense) {
    this.thesisDefense = thesisDefense;
  }

  public abstract Theme getTheme();

  /**
   * Returns the defense of this thesis.
   *
   * @return the class defense
   */
  public ThesisDefense getThesisDefense() {
    return thesisDefense;
  }

  public ThesisDTO toDTO() {
    ThesisDTO dto = new ThesisDTO();
    dto.setId(id);
    dto.setStudent(student.toDTO());
    dto.setInternal(internal.toDTO());
    if (thesisDefense != null) {
      dto.setThesisDefense(thesisDefense.toDTO());
    }
    dto.setTheme(getTheme().toDTO());
    return dto;
  }
}
