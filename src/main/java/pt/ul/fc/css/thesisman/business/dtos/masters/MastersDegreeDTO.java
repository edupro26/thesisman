package pt.ul.fc.css.thesisman.business.dtos.masters;

import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.business.dtos.user.TeacherDTO;

@Component
public class MastersDegreeDTO {

  private Long id;
  private String name;
  private TeacherDTO coordinator;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TeacherDTO getCoordinator() {
    return coordinator;
  }

  public void setCoordinator(TeacherDTO coordinator) {
    this.coordinator = coordinator;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MastersDegreeDTO that = (MastersDegreeDTO) o;

    return id.equals(that.id);
  }

  @Override
  public String toString() {
    return name;
  }
}
