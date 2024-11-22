package pt.ul.fc.css.thesisman.presentation.javafx.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeApplicationDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeApplicationSubmissionDTO;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeDTO;
import pt.ul.fc.css.thesisman.presentation.javafx.session.StudentSession;

public class ThemeApplicationController implements Initializable {

  private static final String VIEWS = "/javafx/views/";

  @FXML private ListView<ThemeDTO> themeList;

  @FXML private ListView<ThemeDTO> applicationList;

  @FXML private Label errorLabel;

  @FXML private Label submittedLabel;

  @FXML private Button deleteButton;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    deleteButton.setVisible(false);
    StudentSession session = StudentSession.getInstance();
    themeList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    applicationList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    String school_year = year + "/" + (year + 1);
    String masters = session.getStudentData().getMasters().getName();

    List<ThemeDTO> themes = requestThemes(masters, school_year);
    themeList.getItems().addAll(themes);
    ThemeApplicationDTO application = requestThemeApplication(session.getStudentData().getId());
    if (application != null) {
      deleteButton.setVisible(true);
      List<ThemeDTO> appliedThemes = application.getThemesList();
      applicationList.getItems().addAll(appliedThemes);
      for (ThemeDTO appliedTheme : appliedThemes) {
        for (ThemeDTO theme : themes) {
          if (appliedTheme.getId().equals(theme.getId())) themeList.getItems().remove(theme);
        }
      }
    }
  }

  @FXML
  void selectThemes(ActionEvent event) {
    errorLabel.setText("");
    submittedLabel.setText("");
    StudentSession currentSession = StudentSession.getInstance();
    if (currentSession.isAuthenticated()) {
      List<ThemeDTO> selectedItems = themeList.getSelectionModel().getSelectedItems();
      List<ThemeDTO> applications = applicationList.getItems();
      if (selectedItems.size() + applications.size() <= 5) {
        applicationList.getItems().addAll(selectedItems);
        themeList.getItems().removeAll(selectedItems);
      } else {
        errorLabel.setText("Não se pode candidatar a mais do que 5 temas");
      }
    } else {
      returnToLoginScene(event, currentSession);
    }
  }

  @FXML
  void removeThemes(ActionEvent event) {
    errorLabel.setText("");
    submittedLabel.setText("");
    StudentSession currentSession = StudentSession.getInstance();
    if (currentSession.isAuthenticated()) {
      List<ThemeDTO> selectedItems = applicationList.getSelectionModel().getSelectedItems();
      if (!selectedItems.isEmpty()) {
        themeList.getItems().addAll(selectedItems);
        applicationList.getItems().removeAll(selectedItems);
      } else {
        errorLabel.setText("Por favor, selecione um tema para remover");
      }
    } else {
      returnToLoginScene(event, currentSession);
    }
  }

  @FXML
  void submitApplication(ActionEvent event) {
    errorLabel.setText("");
    submittedLabel.setText("");
    StudentSession currentSession = StudentSession.getInstance();
    if (currentSession.isAuthenticated()) {
      List<ThemeDTO> themes = applicationList.getItems();
      if (!themes.isEmpty()) {
        ThemeApplicationSubmissionDTO application = new ThemeApplicationSubmissionDTO();
        application.setStudent(currentSession.getStudentData().getId());
        application.setThemes(themes);
        boolean submitted = postThemeApplication(application);
        if (submitted) {
          deleteButton.setVisible(true);
          submittedLabel.setText("Candidatura Submetida");
        }
      } else {
        errorLabel.setText("Por favor, selecione pelo menos um tema");
      }
    } else {
      returnToLoginScene(event, currentSession);
    }
  }

  @FXML
  void deleteApplication(ActionEvent event) {
    errorLabel.setText("");
    submittedLabel.setText("");
    StudentSession currentSession = StudentSession.getInstance();
    if (currentSession.isAuthenticated()) {
      boolean deleted = deleteRequestApplication(currentSession.getStudentData().getId());
      if (deleted) {
        List<ThemeDTO> themes = applicationList.getItems();
        themeList.getItems().addAll(themes);
        applicationList.getItems().clear();
        deleteButton.setVisible(false);
        submittedLabel.setText("Candidatura Cancelada");
      }
    } else {
      returnToLoginScene(event, currentSession);
    }
  }

  @FXML
  void returnToHomeScreen(MouseEvent event) {
    StudentSession currentSession = StudentSession.getInstance();
    try {
      if (currentSession.isAuthenticated()) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS + "home.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(loader.load()));
        currentStage.setTitle("ThesisMan | Início");
        currentStage.show();
      } else {
        currentSession.clearSession();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS + "login.fxml"));
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(loader.load()));
        currentStage.setTitle("ThesisMan | Login");
        currentStage.show();
      }
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private List<ThemeDTO> requestThemes(String masters, String school_year) {
    ResponseEntity<List<ThemeDTO>> responseEntity =
        new RestTemplate()
            .exchange(
                "http://localhost:8080/api/themes?masters=" + masters + "&" + "year=" + school_year,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
    return responseEntity.getBody();
  }

  private ThemeApplicationDTO requestThemeApplication(Long id) {
    try {
      ResponseEntity<ThemeApplicationDTO> responseEntity =
          new RestTemplate()
              .exchange(
                  "http://localhost:8080/api/theme-application/" + id,
                  HttpMethod.GET,
                  null,
                  new ParameterizedTypeReference<>() {});
      return responseEntity.getBody();
    } catch (HttpClientErrorException e) {
      System.out.println("Error: " + e.getMessage());
      return null;
    }
  }

  private boolean deleteRequestApplication(Long id) {
    try {
      ResponseEntity<?> responseEntity =
          new RestTemplate()
              .exchange(
                  "http://localhost:8080/api/theme-application?studentId=" + id,
                  HttpMethod.DELETE,
                  null,
                  new ParameterizedTypeReference<>() {});
      return responseEntity.getStatusCode().is2xxSuccessful();
    } catch (HttpClientErrorException e) {
      System.out.println("Error: " + e.getMessage());
      return false;
    }
  }

  private boolean postThemeApplication(ThemeApplicationSubmissionDTO application) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    HttpEntity<ThemeApplicationSubmissionDTO> request = new HttpEntity<>(application, headers);
    try {
      ResponseEntity<ThemeApplicationDTO> response =
          new RestTemplate()
              .exchange(
                  "http://localhost:8080/api/theme-application",
                  HttpMethod.POST,
                  request,
                  ThemeApplicationDTO.class);
      return response.getStatusCode().is2xxSuccessful();
    } catch (HttpClientErrorException e) {
      System.out.println("Error: " + e.getMessage());
      return false;
    }
  }

  private void returnToLoginScene(ActionEvent event, StudentSession currentSession) {
    currentSession.clearSession();
    FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS + "login.fxml"));
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    try {
      currentStage.setScene(new Scene(loader.load()));
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
    currentStage.setTitle("ThesisMan | Candidaturas");
    currentStage.show();
  }
}
