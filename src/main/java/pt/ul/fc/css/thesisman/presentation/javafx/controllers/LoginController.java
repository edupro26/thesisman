package pt.ul.fc.css.thesisman.presentation.javafx.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pt.ul.fc.css.thesisman.business.dtos.LoginForm;
import pt.ul.fc.css.thesisman.business.dtos.user.StudentDTO;
import pt.ul.fc.css.thesisman.presentation.javafx.session.StudentSession;

public class LoginController implements Initializable {

  private static final String VIEWS = "/javafx/views/";

  @FXML private TextField emailInput;

  @FXML private PasswordField passwordInput;

  @FXML private Label errorLabel;

  @FXML private Button loginButton;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    loginButton.setDefaultButton(true);
  }

  @FXML
  public void loginButtonClicked(ActionEvent event) {
    if (emailInput.getText().isEmpty() || passwordInput.getText().isEmpty()) {
      errorLabel.setText("Por favor introduza o email e a password");
    } else {
      LoginForm loginForm = new LoginForm();
      loginForm.setEmail(emailInput.getText());
      loginForm.setPassword(passwordInput.getText());

      /* Mock funcionality. Any password is accepted */
      sendLogin(event, loginForm);
    }
  }

  private void sendLogin(ActionEvent event, LoginForm loginForm) {
    Task<Boolean> login =
        new Task<>() {
          @Override
          protected Boolean call() {
            return sendRequestAPI(loginForm);
          }
        };

    login.setOnSucceeded(
        e -> {
          if (login.getValue()) {
            loadHomeScene(event);
          } else {
            errorLabel.setText("Por favor verifique as suas credenciais");
          }
        });
    login.setOnFailed(e -> errorLabel.setText("Aconteceu um erro. Por favor tente novamente"));
    new Thread(login).start();
  }

  private boolean sendRequestAPI(LoginForm loginForm) {
    String apiRoute = "http://localhost:8080/api/login";

    // Set up headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    // Make the POST request
    HttpEntity<LoginForm> request = new HttpEntity<>(loginForm, headers);
    try {
      ResponseEntity<StudentDTO> response =
          new RestTemplate().exchange(apiRoute, HttpMethod.POST, request, StudentDTO.class);
      if (response.getStatusCode().is2xxSuccessful()) {
        StudentSession session = StudentSession.getInstance();
        return session.saveSession(response) != null;
      }
      return false;
    } catch (HttpClientErrorException e) {
      System.out.println("Error: " + e.getMessage());
      return false;
    }
  }

  private void loadHomeScene(ActionEvent event) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS + "home.fxml"));
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    try {
      currentStage.setScene(new Scene(loader.load()));
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
    currentStage.setTitle("ThesisMan | Início");
    currentStage.show();
  }
}
