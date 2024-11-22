package pt.ul.fc.css.thesisman.business.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class SchoolYear {

  @Column(name = "ano_letivo", nullable = false)
  private String year;

  public SchoolYear(String year) {
    this.year = year;
  }

  protected SchoolYear() {}

  public void setYear(String schoolYear) {
    this.year = schoolYear;
  }

  public String getYear() {
    return year;
  }

  @Override
  public String toString() {
    return "AnoLetivo[ano=" + year + ']';
  }
}
