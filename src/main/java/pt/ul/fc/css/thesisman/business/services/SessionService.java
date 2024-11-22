package pt.ul.fc.css.thesisman.business.services;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import pt.ul.fc.css.thesisman.business.dtos.user.UserDTO;
import pt.ul.fc.css.thesisman.business.entities.user.CompanyUser;
import pt.ul.fc.css.thesisman.business.entities.user.Student;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;
import pt.ul.fc.css.thesisman.business.entities.user.User;
import pt.ul.fc.css.thesisman.business.repositories.user.UserRepository;
import pt.ul.fc.css.thesisman.business.utils.PasswordUtil;

@SessionScope
@Service
public class SessionService implements Session {

  private boolean isAuthenticated = false;

  private Student studentUser = null;
  private Teacher teacherUser = null;
  private CompanyUser companyUser = null;

  private final UserRepository userRepository;

  @Autowired
  public SessionService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void saveSession(String email, String password) {
    Optional<User> user = userRepository.findByEmail(email);
    if (user.isPresent()) {
      if (user.get() instanceof Student) {
        /* Mock funtionality. Any password is accepted */
        studentUser = (Student) user.get();
        isAuthenticated = true;
      } else if (user.get() instanceof Teacher) {
        /* Mock funtionality. Any password is accepted */
        teacherUser = (Teacher) user.get();
        isAuthenticated = true;
      } else if (user.get() instanceof CompanyUser) {
        byte[] salt = Base64.getDecoder().decode(user.get().getSalt());
        try {
          String hashedPassword = PasswordUtil.hashPassword(password, salt);
          if (user.get().getPassword().equals(hashedPassword)) {
            companyUser = (CompanyUser) user.get();
            isAuthenticated = true;
          }
        } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  @Override
  public void deleteSession() {
    isAuthenticated = false;
    studentUser = null;
    teacherUser = null;
    companyUser = null;
  }

  @Override
  public UserDTO getCurrentUser() {
    UserDTO user = null;
    if (isStudent()) user = studentUser.toDTO();
    else if (isTeacher()) user = teacherUser.toDTO();
    else if (isCompanyUser()) user = companyUser.toDTO();
    return user;
  }

  @Override
  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  public boolean isStudent() {
    return (studentUser != null) && (teacherUser == null) && (companyUser == null);
  }

  public boolean isTeacher() {
    return (teacherUser != null) && (studentUser == null) && (companyUser == null);
  }

  public boolean isCompanyUser() {
    return (companyUser != null) && (teacherUser == null) && (studentUser == null);
  }
}
