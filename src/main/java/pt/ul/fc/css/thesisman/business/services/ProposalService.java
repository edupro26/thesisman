package pt.ul.fc.css.thesisman.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.business.dtos.ProposalDTO;
import pt.ul.fc.css.thesisman.business.entities.proposal.DissertationProposal;
import pt.ul.fc.css.thesisman.business.entities.proposal.ProjectProposal;
import pt.ul.fc.css.thesisman.business.entities.proposal.Proposal;
import pt.ul.fc.css.thesisman.business.entities.theme.DissertationTheme;
import pt.ul.fc.css.thesisman.business.entities.theme.ProjectTheme;
import pt.ul.fc.css.thesisman.business.entities.theme.Theme;
import pt.ul.fc.css.thesisman.business.entities.user.Student;
import pt.ul.fc.css.thesisman.business.repositories.ProposalRepository;
import pt.ul.fc.css.thesisman.business.repositories.theme.ThemeRepository;
import pt.ul.fc.css.thesisman.business.repositories.user.StudentRepository;

@Service
public class ProposalService {

  private final ProposalRepository proposalRepository;
  private final StudentRepository studentRepository;
  private final ThemeRepository themeRepository;

  @Autowired
  public ProposalService(
      ProposalRepository proposalRepository,
      StudentRepository studentRepository,
      ThemeRepository themeRepository) {
    this.proposalRepository = proposalRepository;
    this.studentRepository = studentRepository;
    this.themeRepository = themeRepository;
  }

  public void createProposal(Long studentId, Long themeId) {
    Student student = studentRepository.findById(studentId).get();

    Theme theme = themeRepository.findById(themeId).get();

    if (theme instanceof ProjectTheme) {
      createProjectProposal(student, (ProjectTheme) theme);
    } else if (theme instanceof DissertationTheme) {
      createDissertationProposal(student, (DissertationTheme) theme);
    }
  }

  private void createProjectProposal(Student student, ProjectTheme theme) {
    ProjectProposal proposal = new ProjectProposal(student, theme, null);
    proposalRepository.save(proposal);
  }

  private void createDissertationProposal(Student student, DissertationTheme theme) {
    DissertationProposal proposal = new DissertationProposal(student, theme, null);
    proposalRepository.save(proposal);
  }

  public List<ProposalDTO> findAllNotGradedProposalsAndInternal(Long internal) {
    List<Proposal> proposals =
        proposalRepository.findProposalsWithoutDefenseGradeAndInternal(internal);

    List<ProposalDTO> proposalDTOs = new ArrayList<>();

    for (Proposal proposal : proposals) {
      proposalDTOs.add(proposal.toDTO());
    }

    return proposalDTOs;
  }

  public Proposal findById(Long id) {
    return proposalRepository.findById(id).orElse(null);
  }

  public Optional<ProposalDTO> findByStudentId(Long id) {
    Optional<Proposal> proposal = proposalRepository.findByStudentId(id);
    ProposalDTO proposalDTO = null;
    if (proposal.isPresent()) proposalDTO = proposal.get().toDTO();
    return Optional.ofNullable(proposalDTO);
  }
}
