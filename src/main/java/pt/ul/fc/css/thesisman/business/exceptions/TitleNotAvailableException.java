package pt.ul.fc.css.thesisman.business.exceptions;

public class TitleNotAvailableException extends ApplicationException {

  public TitleNotAvailableException() {
    super("Este título já se encontra em uso");
  }
}
