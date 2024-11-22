package pt.ul.fc.css.thesisman.business.handlers;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.business.dtos.LoginForm;
import pt.ul.fc.css.thesisman.business.dtos.SignupForm;
import pt.ul.fc.css.thesisman.business.dtos.user.CompanyUserDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.StudentDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.TeacherDTO;
import pt.ul.fc.css.thesisman.business.entities.user.CompanyUser;
import pt.ul.fc.css.thesisman.business.exceptions.LoginFailedException;
import pt.ul.fc.css.thesisman.business.exceptions.UserAlreadyExistsException;
import pt.ul.fc.css.thesisman.business.services.UserService;
import pt.ul.fc.css.thesisman.business.utils.PasswordUtil;

@Component
public class LoginHandler {

  private final UserService userService;
  private final SessionHandler sessionHandler;

  @Autowired
  public LoginHandler(UserService userService, SessionHandler sessionHandler) {
    this.userService = userService;
    this.sessionHandler = sessionHandler;
  }

  public boolean createCompanyUser(SignupForm form) throws UserAlreadyExistsException {
    try {
      String name = form.getName();
      String email = form.getEmail();
      String nipc = form.getNipc();
      String password = form.getPassword();
      Optional<StudentDTO> student = userService.findStudentByEmail(email);
      Optional<TeacherDTO> teacher = userService.findTeacherByEmail(email);
      Optional<CompanyUserDTO> companyUser = userService.findCompanyUserByEmail(email);
      if (teacher.isPresent() || companyUser.isPresent() || student.isPresent()) {
        throw new UserAlreadyExistsException();
      }
      CompanyUserDTO created = null;
      if (form.getPasswordConfirmed().equals(password)) {
        byte[] salt = PasswordUtil.generateSalt();
        String hashPassword = PasswordUtil.hashPassword(password, salt);
        String saltString = Base64.getEncoder().encodeToString(salt);
        CompanyUser newUser = new CompanyUser(name, email, hashPassword, nipc);
        newUser.setSalt(saltString);
        created = userService.saveCompanyUser(newUser);
      }
      return created != null;
    } catch (NoSuchAlgorithmException e) {
      System.out.println("Error creating company user: " + e.getMessage());
      return false;
    }
  }

  public StudentDTO getStudentByEmail(String email) throws LoginFailedException {
    Optional<StudentDTO> student = userService.findStudentByEmail(email);
    if (student.isEmpty()) {
      throw new LoginFailedException();
    }
    return student.get();
  }

  public boolean login(LoginForm form) {
    sessionHandler.saveSession(form.getEmail(), form.getPassword());
    return sessionHandler.isAuthenticated();
  }

  public void logout() {
    sessionHandler.deleteSession();
  }

  public SessionHandler getSession() {
    return sessionHandler;
  }
}
