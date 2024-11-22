package pt.ul.fc.css.thesisman.business.handlers;

import java.util.List;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.business.dtos.DefenseDTO;
import pt.ul.fc.css.thesisman.business.entities.defense.Defense;
import pt.ul.fc.css.thesisman.business.entities.defense.ProposalDefense;
import pt.ul.fc.css.thesisman.business.entities.defense.ThesisDefense;
import pt.ul.fc.css.thesisman.business.entities.proposal.DissertationProposal;
import pt.ul.fc.css.thesisman.business.entities.proposal.ProjectProposal;
import pt.ul.fc.css.thesisman.business.entities.proposal.Proposal;
import pt.ul.fc.css.thesisman.business.services.DefenseService;
import pt.ul.fc.css.thesisman.business.services.ProposalService;
import pt.ul.fc.css.thesisman.business.services.StatsService;
import pt.ul.fc.css.thesisman.business.services.ThesisService;

@Component
public class AssignGradeHandler {

  private final DefenseService defenseService;

  private final ProposalService proposalService;

  private final ThesisService thesisService;

  private final SessionHandler sessionHandler;
  private final StatsService statsService;

  public AssignGradeHandler(
          DefenseService defenseService,
          ProposalService proposalService,
          ThesisService thesisService,
          SessionHandler sessionHandler, StatsService statsService) {
    this.defenseService = defenseService;
    this.proposalService = proposalService;
    this.thesisService = thesisService;
    this.sessionHandler = sessionHandler;
    this.statsService = statsService;
  }

  public List<DefenseDTO> getProposalsDefenses() {
    return defenseService.findByInternalTeacher(sessionHandler.getCurrentUser().getId());
  }

  public List<DefenseDTO> getThesesDefenses() {
    return defenseService.findByPresident(sessionHandler.getCurrentUser().getId());
  }

  public void assignGrade(Long id, float grade) {
    Defense defense =
        defenseService
            .findDefenseById(id)
            .orElseThrow(() -> new IllegalArgumentException("Defense not found"));

    if (defense instanceof ThesisDefense) {
      ThesisDefense thesisDefense = (ThesisDefense) defense;
      if (!thesisDefense
          .getJury()
          .getPresident()
          .getId()
          .equals(sessionHandler.getCurrentUser().getId())) {
        throw new IllegalArgumentException("You are not the president of this defense.");
      }
    } else if (defense instanceof ProposalDefense) {
      ProposalDefense proposalDefense = (ProposalDefense) defense;
      if (!proposalDefense
          .getProposal()
          .getInternal()
          .getId()
          .equals(sessionHandler.getCurrentUser().getId())) {
        throw new IllegalArgumentException("You are not the internal advisor of this defense.");
      }
    }
    defense.setGrade(grade);
    defenseService.saveDefense(defense);

    if (defense instanceof ProposalDefense && grade < 10) {
      Proposal proposal = ((ProposalDefense) defense).getProposal();

      if (!proposal.getInternal().getId().equals(sessionHandler.getCurrentUser().getId())) {
        throw new IllegalArgumentException("You are not the internal advisor of this proposal.");
      }

      Long studentId = proposal.getStudent().getId();

      if (proposal instanceof ProjectProposal) {
        Long themeId = ((ProjectProposal) proposal).getTheme().getId();
        proposalService.createProposal(studentId, themeId);
      } else {
        Long themeId = ((DissertationProposal) proposal).getTheme().getId();
        proposalService.createProposal(studentId, themeId);
      }
    }

    if (defense instanceof ProposalDefense && grade >= 10) {
      Proposal proposal = ((ProposalDefense) defense).getProposal();

      Long studentId = proposal.getStudent().getId();

      if (proposal instanceof ProjectProposal) {
        Long themeId = ((ProjectProposal) proposal).getTheme().getId();
        thesisService.createThesis(studentId, themeId);
      } else {
        Long themeId = ((DissertationProposal) proposal).getTheme().getId();
        thesisService.createThesis(studentId, themeId);
      }
    }

    statsService.updateStats();
  }
}
