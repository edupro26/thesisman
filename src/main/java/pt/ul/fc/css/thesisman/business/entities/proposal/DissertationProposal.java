package pt.ul.fc.css.thesisman.business.entities.proposal;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import pt.ul.fc.css.thesisman.business.entities.defense.ProposalDefense;
import pt.ul.fc.css.thesisman.business.entities.theme.DissertationTheme;
import pt.ul.fc.css.thesisman.business.entities.user.Student;

/**
 * Represents a dissertation proposal. Dissertation proposal is mapped to a table named 'proposta'.
 * Dissertation proposal is mapped to a table named 'proposta'.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "proposta_dissertacao")
@PrimaryKeyJoinColumn(name = "proposta_id")
public class DissertationProposal extends Proposal {

  /** The theme chosen for the dissertation. */
  @ManyToOne
  @JoinColumn(name = "tema_id")
  private DissertationTheme theme;

  /**
   * Constructs a new DissertationProposal.
   *
   * @param student the student that filled the proposal for the dissertation
   * @param theme the theme chosen for the dissertation
   * @param proposalDefense the proposal defense associated with the proposal
   */
  public DissertationProposal(
      Student student, DissertationTheme theme, ProposalDefense proposalDefense) {
    super(student, proposalDefense, theme.getInternal());
    this.theme = theme;
  }

  /** No-arg constructor */
  protected DissertationProposal() {}

  /**
   * Sets the theme chosen for the dissertation.
   *
   * @param theme the theme chosen for the dissertation
   */
  public void setTheme(DissertationTheme theme) {
    this.theme = theme;
  }

  /**
   * Gets the theme chosen for the dissertation.
   *
   * @return the theme chosen for the dissertation
   */
  public DissertationTheme getTheme() {
    return theme;
  }

  /**
   * Gets the external company that will advise the dissertation.
   *
   * @return the external company that will advise the dissertation
   */
  @Override
  public String toString() {
    return "PropostaDissertacao["
        + "id="
        + getId()
        + ", "
        + "aluno= "
        + getStudent()
        + ", "
        + "tema="
        + theme
        + ", "
        + "orientadorInterno="
        + getInternal()
        + ", "
        + "defesaProposta="
        + getProposalDefense()
        + ']';
  }
}
