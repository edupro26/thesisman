package pt.ul.fc.css.thesisman.business.exceptions;

public class ListNotReceivedException extends ApplicationException {

  public ListNotReceivedException() {
    super("Could not retrieve list from server.");
  }
}
