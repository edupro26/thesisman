package pt.ul.fc.css.thesisman.business.controllers;

import java.util.Optional;
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
import pt.ul.fc.css.thesisman.business.dtos.ThesisDTO;
import pt.ul.fc.css.thesisman.business.exceptions.ApplicationException;
import pt.ul.fc.css.thesisman.business.handlers.FileHandler;

@RestController
@RequestMapping("/api")
public class ThesisController {

  private final FileHandler fileHandler;

  public ThesisController(FileHandler fileHandler) {
    this.fileHandler = fileHandler;
  }

  @GetMapping("/thesis")
  public ResponseEntity<?> getStudentProposal(@RequestParam("studentId") Long studentId) {
    Optional<ThesisDTO> thesisDTO = fileHandler.findThesisByStudentId(studentId);
    if (thesisDTO.isPresent()) {
      return ResponseEntity.ok().body(thesisDTO.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping(value = "/thesis/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadThesis(
      @RequestPart("file") MultipartFile file, @PathVariable("id") Long id) {
    try {
      fileHandler.saveThesisFile(file, id);
      return ResponseEntity.status(HttpStatus.OK).body("Thesis uploaded successfully");
    } catch (ApplicationException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to upload thesis");
    }
  }
}
