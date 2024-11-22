package pt.ul.fc.css.thesisman.business.entities.defense;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import pt.ul.fc.css.thesisman.business.dtos.DefenseDTO;
import pt.ul.fc.css.thesisman.business.entities.defense.location.Reservation;
import pt.ul.fc.css.thesisman.business.entities.proposal.Proposal;

/**
 * Represents a proposal defense of a thesis. It is submitted in January. The proposal defense lasts
 * 60 minutes. Maps to a table named 'defesa_proposta'.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "defesa_proposta")
@PrimaryKeyJoinColumn(name = "defesa_id")
public final class ProposalDefense extends Defense {

  /** The jury that will evaluate the defense. */
  @OneToOne
  @JoinColumn(name = "juri_id")
  private Jury jury;

  /** The proposal defense's duration. */
  @Column(name = "duracao", nullable = false)
  private int duration;

  /** The proposal that will be defended. */
  @OneToOne
  @JoinColumn(name = "proposta_id")
  private Proposal proposal;

  /**
   * Constructs a new ProposalDefense.
   *
   * @param reservation where and when the defence will take place
   * @param jury the jury that will evaluate the defense
   * @param grade the grade given to the defense
   */
  public ProposalDefense(Reservation reservation, Jury jury, float grade, Proposal proposal) {
    super(reservation, grade);
    this.jury = jury;
    this.proposal = proposal;
    this.duration = 60;
  }

  /** No-arg constructor */
  public ProposalDefense() {}

  /**
   * Sets the jury that will evaluate the defense.
   *
   * @param jury the jury that will evaluate the defense
   */
  public void setJury(Jury jury) {
    this.jury = jury;
  }

  /**
   * Sets the proposal defense's duration.
   *
   * @param duration the proposal defense's duration
   */
  public void setDuration(int duration) {
    this.duration = duration;
  }

  /**
   * Sets the proposal that will be defended.
   *
   * @param proposal the proposal that will be defended
   */
  public void setProposal(Proposal proposal) {
    this.proposal = proposal;
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
   * Gets the proposal defense's duration.
   *
   * @return the proposal defense's duration
   */
  public int getDuration() {
    return duration;
  }

  /**
   * Gets the proposal that will be defended.
   *
   * @return the proposal that will be defended
   */
  public Proposal getProposal() {
    return proposal;
  }

  /**
   * Returns a string representation of the proposal defense.
   *
   * @return a string representation of the proposal defense
   */
  @Override
  public String toString() {
    return "DefesaProposta["
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
    defenseDTO.setThemeTitle(proposal.getTheme().getTitle());
    defenseDTO.setStudent(proposal.getStudent().toDTO());
    defenseDTO.setType("Proposta");
    defenseDTO.setGrade(getGrade());
    defenseDTO.setReservation(getReservation());
    defenseDTO.setTheme(proposal.getTheme().toDTO());
    return defenseDTO;
  }
}
