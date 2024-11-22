package pt.ul.fc.css.thesisman.business.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeApplicationDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeApplicationSubmissionDTO;
import pt.ul.fc.css.thesisman.business.handlers.ThemeApplicationHandler;

@RestController
@RequestMapping("/api")
public class ThemeApplicationController {

  private final ThemeApplicationHandler themeApplicationHandler;

  @Autowired
  public ThemeApplicationController(ThemeApplicationHandler themeApplicationHandler) {
    this.themeApplicationHandler = themeApplicationHandler;
  }

  @PostMapping("/theme-application")
  public ResponseEntity<?> submitThemeApplication(
      @RequestBody ThemeApplicationSubmissionDTO themeApplicationList) {
    try {
      ThemeApplicationDTO application =
          themeApplicationHandler.submitThemeApplication(themeApplicationList);
      return ResponseEntity.ok().body(application);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/theme-application/{id}")
  public ResponseEntity<?> getThemeApplication(@PathVariable Long id) {
    Optional<ThemeApplicationDTO> applicationDTO = themeApplicationHandler.findByStudentId(id);
    if (applicationDTO.isPresent()) {
      return ResponseEntity.ok().body(applicationDTO.get());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/theme-application")
  public ResponseEntity<?> cancelThemeApplication(
      @RequestParam(value = "studentId") String studentId) {
    try {
      themeApplicationHandler.cancelThemeApplication(Long.parseLong(studentId));
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
