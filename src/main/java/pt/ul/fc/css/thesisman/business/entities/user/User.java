package pt.ul.fc.css.thesisman.business.entities.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/**
 * Represents a user with its default attributes. This interface defines methods for setting and
 * getting a user's ID, name, email, and password.
 *
 * @author Eduardo Proença (57551)
 * @author Manuel Barral (52026)
 * @author Tiago Oliveira (54979)
 */
@Entity
@Table(name = "utilizador")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "nome", nullable = false)
  private String name;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "palavra-passe", nullable = false)
  private String password;

  @Column(name = "salt")
  private String salt;

  /**
   * Constructs a new user
   *
   * @param email the email for this user
   * @param password the password of this user
   */
  protected User(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  /** No-arg constructor */
  protected User() {}

  /**
   * Sets the ID of this user
   *
   * @param id the unique identifier for this user
   */
  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the email address of this user
   *
   * @param email the email for this user
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Sets the password of this user
   *
   * @param password the password for this user
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Returns the ID of the user
   *
   * @return the unique identifier for this user
   */
  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * Returns the email of this user
   *
   * @return the email of this user
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns the password of this user
   *
   * @return the password of this user
   */
  public String getPassword() {
    return password;
  }

  /**
   * Returns the salt of this user
   *
   * @return the salt of this user
   */
  public String getSalt() {
    return salt;
  }

  /**
   * Sets the salt of this user
   *
   * @param salt the salt for this user
   */
  public void setSalt(String salt) {
    this.salt = salt;
  }
}
