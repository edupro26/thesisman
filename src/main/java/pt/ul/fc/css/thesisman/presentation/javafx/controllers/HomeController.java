package pt.ul.fc.css.thesisman.presentation.javafx.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ul.fc.css.thesisman.business.dtos.user.StudentDTO;
import pt.ul.fc.css.thesisman.presentation.javafx.session.StudentSession;

public class HomeController implements Initializable {

  private static final String VIEWS = "/javafx/views/";

  @FXML private Label nameLabel;

  @FXML private Label numberLabel;

  @FXML private Label mastersLabel;

  @FXML private Label averageLabel;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    StudentSession currentSession = StudentSession.getInstance();
    StudentDTO studentData = currentSession.getStudentData();
    nameLabel.setText(studentData.getName());
    String email = studentData.getEmail();
    numberLabel.setText(email.split("@")[0].substring(2, 7));
    mastersLabel.setText(studentData.getMasters().getName());
    averageLabel.setText(studentData.getAverage().toString());
  }

  @FXML
  public void loadApplicationsScene(ActionEvent event) {
    StudentSession currentSession = StudentSession.getInstance();
    if (currentSession.isAuthenticated()) {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS + "applications.fxml"));
      Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      try {
        currentStage.setScene(new Scene(loader.load()));
      } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
      }
      currentStage.setTitle("ThesisMan | Candidaturas");
      currentStage.show();
    } else {
      returnToLoginScene(event, currentSession);
    }
  }

  @FXML
  public void loadSubmissionsScene(ActionEvent event) {
    StudentSession currentSession = StudentSession.getInstance();
    if (currentSession.isAuthenticated()) {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS + "submissions.fxml"));
      Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      try {
        currentStage.setScene(new Scene(loader.load()));
      } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
      }
      currentStage.setTitle("ThesisMan | Submissão de Documentos");
      currentStage.show();
    } else {
      returnToLoginScene(event, currentSession);
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
