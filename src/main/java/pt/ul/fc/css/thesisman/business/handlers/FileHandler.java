package pt.ul.fc.css.thesisman.business.handlers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pt.ul.fc.css.thesisman.business.dtos.ProposalDTO;
import pt.ul.fc.css.thesisman.business.dtos.ThesisDTO;
import pt.ul.fc.css.thesisman.business.exceptions.ApplicationException;
import pt.ul.fc.css.thesisman.business.services.ProposalService;
import pt.ul.fc.css.thesisman.business.services.StorageService;
import pt.ul.fc.css.thesisman.business.services.ThesisService;

@Component
public class FileHandler {

  private final ProposalService proposalService;

  private final ThesisService thesisService;

  private final StorageService storageService;

  @Autowired
  public FileHandler(
      ProposalService proposalService, ThesisService thesisService, StorageService storageService) {
    this.proposalService = proposalService;
    this.thesisService = thesisService;
    this.storageService = storageService;
  }

  public Optional<ProposalDTO> findProposalByStudentId(Long id) {
    return proposalService.findByStudentId(id);
  }

  public Optional<ThesisDTO> findThesisByStudentId(Long id) {
    return thesisService.findByStudentId(id);
  }

  public void saveProposalFile(MultipartFile file, Long id) throws ApplicationException {
    storageService.store(file);
  }

  public void saveThesisFile(MultipartFile file, Long id) throws ApplicationException {
    storageService.store(file);
  }
}
