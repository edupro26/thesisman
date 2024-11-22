package pt.ul.fc.css.thesisman.business.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pt.ul.fc.css.thesisman.business.dtos.ProposalDTO;
import pt.ul.fc.css.thesisman.business.exceptions.ApplicationException;
import pt.ul.fc.css.thesisman.business.handlers.FileHandler;

@RestController
@RequestMapping("/api")
public class ProposalController {

  private final FileHandler fileHandler;

  @Autowired
  public ProposalController(FileHandler fileHandler) {
    this.fileHandler = fileHandler;
  }

  @GetMapping("/proposal")
  public ResponseEntity<?> getStudentProposal(@RequestParam("studentId") Long studentId) {
    Optional<ProposalDTO> proposalDTO = fileHandler.findProposalByStudentId(studentId);
    if (proposalDTO.isPresent()) {
      return ResponseEntity.ok().body(proposalDTO.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping(value = "/proposal/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadProposal(
      @RequestPart("file") MultipartFile file, @PathVariable("id") Long id) {
    try {
      fileHandler.saveProposalFile(file, id);
      return ResponseEntity.ok().body("Proposal uploaded successfully");
    } catch (ApplicationException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to upload proposal");
    }
  }
}
