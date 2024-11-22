package pt.ul.fc.css.thesisman.business.dtos.theme;

import java.util.List;
import pt.ul.fc.css.thesisman.business.dtos.masters.MastersDegreeDTO;
import pt.ul.fc.css.thesisman.business.entities.SchoolYear;

public class ThemeDTO {

  private Long id;
  private String title;
  private String description;
  private double remuneration;
  private List<MastersDegreeDTO> masters;
  private SchoolYear schoolYear;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getRemuneration() {
    return remuneration;
  }

  public void setRemuneration(double remuneration) {
    this.remuneration = remuneration;
  }

  public List<MastersDegreeDTO> getMasters() {
    return masters;
  }

  public void setMasters(List<MastersDegreeDTO> masters) {
    this.masters = masters;
  }

  public SchoolYear getSchoolYear() {
    return schoolYear;
  }

  public void setSchoolYear(SchoolYear schoolYear) {
    this.schoolYear = schoolYear;
  }

  @Override
  public String toString() {
    return "Título: "
        + title
        + "\nDescrição: "
        + description
        + "\nRemuneração: "
        + remuneration
        + "\nMestrados: "
        + masters;
  }
}
