package pt.ul.fc.css.thesisman.presentation.javafx.session;

import org.springframework.http.ResponseEntity;
import pt.ul.fc.css.thesisman.business.dtos.user.StudentDTO;

public class StudentSession implements GUISession {

  private boolean isAuthenticated;

  private StudentDTO studentData;

  private static StudentSession instance;

  private StudentSession() {
    isAuthenticated = false;
    studentData = new StudentDTO();
  }

  public static StudentSession getInstance() {
    if (instance == null) {
      instance = new StudentSession();
    }
    return instance;
  }

  @Override
  public StudentDTO saveSession(ResponseEntity<StudentDTO> loginResponse) {
    studentData = loginResponse.getBody();
    isAuthenticated = true;
    return studentData;
  }

  @Override
  public void clearSession() {
    isAuthenticated = false;
    studentData = null;
  }

  @Override
  public StudentDTO getStudentData() {
    return studentData;
  }

  @Override
  public boolean isAuthenticated() {
    return isAuthenticated;
  }
}
