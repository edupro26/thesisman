package pt.ul.fc.css.thesisman.business.entities.proposal;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import pt.ul.fc.css.thesisman.business.entities.defense.ProposalDefense;
import pt.ul.fc.css.thesisman.business.entities.theme.ProjectTheme;
import pt.ul.fc.css.thesisman.business.entities.user.CompanyUser;
import pt.ul.fc.css.thesisman.business.entities.user.Student;

/**
 * Represents a project proposal. Project proposal is mapped to a table named 'proposta'. Project
 * proposal is mapped to a table named 'proposta'.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "proposta_projeto")
@PrimaryKeyJoinColumn(name = "proposta_id")
public class ProjectProposal extends Proposal {

  /** The theme chosen for the project. */
  @ManyToOne
  @JoinColumn(name = "tema_id")
  private ProjectTheme theme;

  /** The external company that will advise the project. */
  @ManyToOne
  @JoinColumn(name = "orientador_externo")
  private CompanyUser external;

  /**
   * Constructs a new ProjectProposal.
   *
   * @param student the student that filled the proposal for the project
   * @param theme the theme chosen for the project
   * @param proposalDefense the proposal defense associated with the proposal
   */
  public ProjectProposal(Student student, ProjectTheme theme, ProposalDefense proposalDefense) {
    super(student, proposalDefense, theme.getInternal());
    this.theme = theme;
    this.external = theme.getExternal();
  }

  /** No-arg constructor */
  protected ProjectProposal() {}

  /**
   * Sets the theme chosen for the project.
   *
   * @param theme the theme chosen for the project
   */
  public void setTheme(ProjectTheme theme) {
    this.theme = theme;
  }

  /**
   * Sets the external company that will advise the project.
   *
   * @param external the external company that will advise the project
   */
  public void setExternal(CompanyUser external) {
    this.external = external;
  }

  /**
   * Gets the theme chosen for the project.
   *
   * @return the theme chosen for the project
   */
  public ProjectTheme getTheme() {
    return theme;
  }

  /**
   * Gets the external company that will advise the project.
   *
   * @return the external company that will advise the project
   */
  public CompanyUser getExternal() {
    return external;
  }

  /**
   * Returns a string representation of the project proposal.
   *
   * @return a string representation of the project proposal
   */
  @Override
  public String toString() {
    return "PropostaProjeto["
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
        + "orientadorExterno="
        + external
        + ", "
        + "defesaProposta="
        + getProposalDefense()
        + ']';
  }
}
