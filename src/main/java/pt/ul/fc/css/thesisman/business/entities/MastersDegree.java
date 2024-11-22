package pt.ul.fc.css.thesisman.business.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import pt.ul.fc.css.thesisman.business.dtos.masters.MastersDegreeDTO;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;

/** Represents a Master's Degree. */
@Entity
@Table(name = "mestrado")
public class MastersDegree {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "nome", nullable = false, unique = true)
  private String name;

  @OneToOne
  @JoinColumn(name = "administrador_id")
  private Teacher coordinator;

  public MastersDegree(String name, Teacher coordinator) {
    this.name = name;
    this.coordinator = coordinator;
  }

  /** No-arg constructor */
  protected MastersDegree() {}

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Sets the name of the degree.
   *
   * @param name The name of the degree.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the coordinator of this master's degree
   *
   * @param coordinator the coordinator of this master's degree
   */
  public void setCoordinator(Teacher coordinator) {
    this.coordinator = coordinator;
  }

  public Long getId() {
    return id;
  }

  /**
   * Returns the name of the degree.
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the coordinator of this master's degree
   *
   * @return the coordinator of this master's degree
   */
  public Teacher getCoordinator() {
    return coordinator;
  }

  public MastersDegreeDTO toDTO() {
    MastersDegreeDTO dto = new MastersDegreeDTO();
    dto.setId(id);
    dto.setName(name);
    dto.setCoordinator(coordinator.toDTO());
    return dto;
  }
}
