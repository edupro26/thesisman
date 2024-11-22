package pt.ul.fc.css.thesisman.business.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.business.dtos.user.UserDTO;
import pt.ul.fc.css.thesisman.business.services.SessionService;

@Component
public class SessionHandler {

  private final SessionService sessionService;

  @Autowired
  public SessionHandler(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  public void saveSession(String email, String password) {
    sessionService.saveSession(email, password);
  }

  public void deleteSession() {
    sessionService.deleteSession();
  }

  public UserDTO getCurrentUser() {
    return sessionService.getCurrentUser();
  }

  public boolean isAuthenticated() {
    return sessionService.isAuthenticated();
  }

  public boolean isStudent() {
    return sessionService.isStudent();
  }

  public boolean isTeacher() {
    return sessionService.isTeacher();
  }

  public boolean isCompanyUser() {
    return sessionService.isCompanyUser();
  }
}
