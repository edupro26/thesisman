package pt.ul.fc.css.thesisman.business.dtos;

import org.springframework.stereotype.Component;

@Component
public class SignupForm {

  private String name;
  private String email;
  private String nipc;
  private String password;
  private String passwordConfirmed;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNipc() {
    return nipc;
  }

  public void setNipc(String nipc) {
    this.nipc = nipc;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPasswordConfirmed() {
    return passwordConfirmed;
  }

  public void setPasswordConfirmed(String passwordConfirmed) {
    this.passwordConfirmed = passwordConfirmed;
  }
}
