package pt.ul.fc.css.thesisman.business.exceptions;

public class LoginFailedException extends ApplicationException {

  public LoginFailedException() {
    super("Login failed. Please verify your credentials.");
  }
}
