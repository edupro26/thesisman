package pt.ul.fc.css.thesisman.business.services;

import pt.ul.fc.css.thesisman.business.dtos.user.UserDTO;

public interface Session {

  void saveSession(String email, String password);

  void deleteSession();

  UserDTO getCurrentUser();

  boolean isAuthenticated();
}
