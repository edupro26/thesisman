package pt.ul.fc.css.thesisman.business.entities.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import pt.ul.fc.css.thesisman.business.dtos.user.CompanyUserDTO;

/**
 * Represents a user from a company.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "utilizador_empresarial")
public final class CompanyUser extends User {

  @Column(name = "nipc", nullable = false)
  private String nipc;

  /**
   * Constructs a new CompanyUser.
   *
   * @param name the name of this user
   * @param email the email of this user
   * @param password the password of this user
   * @param nipc the NIPC of this user's company
   */
  public CompanyUser(String name, String email, String password, String nipc) {
    super(name, email, password);
    this.nipc = nipc;
  }

  /** No-arg constructor */
  protected CompanyUser() {}

  /**
   * Sets the nipc of this user
   *
   * @param nipc the nipc of this user
   */
  public void setNipc(String nipc) {
    this.nipc = nipc;
  }

  /**
   * Returns the nipc of this user
   *
   * @return the nipc of this user
   */
  public String getNipc() {
    return nipc;
  }

  /**
   * Returns a string representation of this user
   *
   * @return a string representation of this user
   */
  @Override
  public String toString() {
    return "UtilizadoEmpresarial["
        + "id="
        + getId()
        + ", "
        + "nome="
        + getName()
        + ", "
        + "email="
        + getEmail()
        + ", "
        + "palavra-passe="
        + getPassword()
        + ", "
        + "NIPC="
        + nipc
        + ']';
  }

  public CompanyUserDTO toDTO() {
    CompanyUserDTO dto = new CompanyUserDTO();
    dto.setId(getId());
    dto.setName(getName());
    dto.setEmail(getEmail());
    dto.setNipc(getNipc());
    return dto;
  }
}
