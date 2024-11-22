package pt.ul.fc.css.thesisman.business.dtos;

import java.time.LocalDateTime;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.StudentDTO;
import pt.ul.fc.css.thesisman.business.entities.defense.location.Reservation;

public class DefenseDTO {

  private Long id;

  private LocalDateTime date;

  private int duration;

  private String themeTitle;

  private StudentDTO student;

  private String type;

  private Float grade;

  private Reservation reservation;

  private ThemeDTO theme;

  private JuryDTO jury;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public String getThemeTitle() {
    return themeTitle;
  }

  public void setThemeTitle(String themeTitle) {
    this.themeTitle = themeTitle;
  }

  public StudentDTO getStudent() {
    return student;
  }

  public void setStudent(StudentDTO student) {
    this.student = student;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Float getGrade() {
    return grade;
  }

  public void setGrade(Float grade) {
    this.grade = grade;
  }

  public Reservation getReservation() {
    return reservation;
  }

  public void setReservation(Reservation reservation) {
    this.reservation = reservation;
  }

  public ThemeDTO getTheme() {
    return theme;
  }

  public void setTheme(ThemeDTO theme) {
    this.theme = theme;
  }

  public JuryDTO getJury() {
      return jury;
  }

  public void setJury(JuryDTO jury) {
      this.jury = jury;
  }
}
