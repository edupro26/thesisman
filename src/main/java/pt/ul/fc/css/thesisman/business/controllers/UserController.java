package pt.ul.fc.css.thesisman.business.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ul.fc.css.thesisman.business.dtos.LoginForm;
import pt.ul.fc.css.thesisman.business.dtos.user.StudentDTO;
import pt.ul.fc.css.thesisman.business.exceptions.LoginFailedException;
import pt.ul.fc.css.thesisman.business.handlers.LoginHandler;

@RestController
@RequestMapping("/api")
public class UserController {

  private final LoginHandler loginHandler;

  @Autowired
  public UserController(LoginHandler loginHandler) {
    this.loginHandler = loginHandler;
  }

  @PostMapping("/login")
  public ResponseEntity<?> studentLogin(@RequestBody LoginForm loginForm) {
    try {
      StudentDTO student = loginHandler.getStudentByEmail(loginForm.getEmail());
      return ResponseEntity.ok().body(student);
    } catch (LoginFailedException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }
}
