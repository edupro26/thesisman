package pt.ul.fc.css.thesisman.business.dtos.user;

import org.springframework.stereotype.Component;

@Component
public class CompanyUserDTO extends UserDTO {

  private String nipc;

  public String getNipc() {
    return nipc;
  }

  public void setNipc(String nipc) {
    this.nipc = nipc;
  }
}
