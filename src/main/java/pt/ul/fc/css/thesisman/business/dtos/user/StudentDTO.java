package pt.ul.fc.css.thesisman.business.dtos.user;

import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.business.dtos.masters.MastersDegreeDTO;

@Component
public class StudentDTO extends UserDTO {

  private MastersDegreeDTO masters;
  private Double average;

  public MastersDegreeDTO getMasters() {
    return masters;
  }

  public void setMasters(MastersDegreeDTO masters) {
    this.masters = masters;
  }

  public Double getAverage() {
    return average;
  }

  public void setAverage(Double average) {
    this.average = average;
  }
}
