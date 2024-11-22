package pt.ul.fc.css.thesisman.business.entities.proposal;

import jakarta.persistence.*;
import pt.ul.fc.css.thesisman.business.dtos.ProposalDTO;
import pt.ul.fc.css.thesisman.business.entities.defense.ProposalDefense;
import pt.ul.fc.css.thesisman.business.entities.theme.Theme;
import pt.ul.fc.css.thesisman.business.entities.user.Student;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;

/**
 * Represents a proposal. Proposal is mapped to a table named 'proposta'. Can be a proposal for a
 * dissertation or a project. The proposal is filled by a student. Proposal is mapped to a table
 * named 'proposta'.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "proposta")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Proposal {

  /** The proposal's id. */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  /** The student that filled the proposal. */
  @ManyToOne
  @JoinColumn(name = "aluno_id")
  private Student student;

  /** The internal teacher that will advise the proposal. */
  @ManyToOne
  @JoinColumn(name = "orientador_interno")
  private Teacher internal;

  /** Defenses proposed for the project/dissertation. */
  @OneToOne
  @JoinColumn(name = "proposta_proposta_id")
  private ProposalDefense proposalDefense;

  /**
   * Constructs a new Proposal.
   *
   * @param student the student that filled the proposal
   * @param proposalDefense the proposal defense associated with the proposal
   */
  protected Proposal(Student student, ProposalDefense proposalDefense, Teacher internal) {
    this.student = student;
    this.proposalDefense = proposalDefense;
    this.internal = internal;
  }

  /** No-arg constructor */
  protected Proposal() {}

  /**
   * Sets the defense of the proposal.
   *
   * @param defense the defense to be set
   */
  public void setDefense(ProposalDefense defense) {
    this.proposalDefense = defense;
  }

  /**
   * Sets the proposal's id.
   *
   * @param id the proposal's id
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Sets the student that filled the proposal.
   *
   * @param student the student that filled the proposal
   */
  public void setStudent(Student student) {
    this.student = student;
  }

  /**
   * Sets the internal teacher that will advise the proposal.
   *
   * @param internal the internal teacher that will advise the proposal
   */
  public void setInternal(Teacher internal) {
    this.internal = internal;
  }

  /**
   * Returns the proposal's id.
   *
   * @return the proposal's id
   */
  public long getId() {
    return id;
  }

  /**
   * Returns the student that filled the proposal.
   *
   * @return the student that filled the proposal
   */
  public Student getStudent() {
    return student;
  }

  /**
   * Gets the internal teacher that will advise the proposal.
   *
   * @return the internal teacher that will advise the proposal
   */
  public Teacher getInternal() {
    return internal;
  }

  /**
   * Returns the proposal defense.
   *
   * @return the proposal defense
   */
  public ProposalDefense getProposalDefense() {
    return proposalDefense;
  }

  public abstract Theme getTheme();

  public ProposalDTO toDTO() {
    ProposalDTO dto = new ProposalDTO();
    dto.setId(id);
    dto.setStudent(student.toDTO());
    dto.setInternal(internal.toDTO());
    if (proposalDefense != null) {
      dto.setProposalDefense(proposalDefense.toDTO());
    }
    dto.setTheme(getTheme().toDTO());
    return dto;
  }
}
