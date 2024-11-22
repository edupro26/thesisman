package pt.ul.fc.css.thesisman.presentation.javafx.session;

import org.springframework.http.ResponseEntity;
import pt.ul.fc.css.thesisman.business.dtos.user.StudentDTO;

public interface GUISession {

  StudentDTO saveSession(ResponseEntity<StudentDTO> loginResponse);

  void clearSession();

  StudentDTO getStudentData();

  boolean isAuthenticated();
}
