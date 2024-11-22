package pt.ul.fc.css.thesisman.business.entities.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import pt.ul.fc.css.thesisman.business.dtos.user.TeacherDTO;

/**
 * Represents a teacher
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "utilizador_docente")
public final class Teacher extends User {

  /**
   * Constructs a new Teacher.
   *
   * @param name the name of this teacher
   * @param email the email of this teacher
   * @param password the password of this teacher
   */
  public Teacher(String name, String email, String password) {
    super(name, email, password);
  }

  /** No-arg constructor */
  protected Teacher() {}

  /**
   * Returns a string representation of this user
   *
   * @return a string representation of this user
   */
  @Override
  public String toString() {
    return "Docente["
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
        + ']';
  }

  public TeacherDTO toDTO() {
    TeacherDTO dto = new TeacherDTO();
    dto.setId(getId());
    dto.setName(getName());
    dto.setEmail(getEmail());
    return dto;
  }
}
