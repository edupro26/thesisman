package pt.ul.fc.css.thesisman.business.dtos.theme;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ThemeSubmissionDTO {

  private String title;

  private String description;

  private double remuneration;

  private List<Long> selectedMasters;

  private Long internal;

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

  public List<Long> getSelectedMasters() {
    return selectedMasters;
  }

  public void setSelectedMasters(List<Long> selectedMasters) {
    this.selectedMasters = selectedMasters;
  }

  public Long getInternal() {
    return internal;
  }

  public void setInternal(Long internal) {
    this.internal = internal;
  }
}
