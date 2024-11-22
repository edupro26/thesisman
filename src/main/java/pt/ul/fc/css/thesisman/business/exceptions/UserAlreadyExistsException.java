package pt.ul.fc.css.thesisman.business.exceptions;

public class UserAlreadyExistsException extends ApplicationException {

  public UserAlreadyExistsException() {
    super("Este email já se encontra em uso por outra conta");
  }
}
