package pt.ul.fc.css.thesisman.business.exceptions;

public class NegativeRemunerationException extends ApplicationException {

  public NegativeRemunerationException() {
    super("O valor de remuneração deve ser um valor posivo");
  }
}
