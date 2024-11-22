package pt.ul.fc.css.thesisman.presentation.javafx.controllers;

import java.io.File;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pt.ul.fc.css.thesisman.business.dtos.ProposalDTO;
import pt.ul.fc.css.thesisman.business.dtos.ThesisDTO;
import pt.ul.fc.css.thesisman.presentation.javafx.session.StudentSession;

public class FileSubmissionController implements Initializable {

  private static final String VIEWS = "/javafx/views/";

  @FXML private Label errorLabel1;

  @FXML private Label errorLabel2;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    errorLabel1.setVisible(false);
    errorLabel2.setVisible(false);
  }

  @FXML
  void chooseProposalFile(ActionEvent event) {
    errorLabel1.setVisible(false);
    errorLabel2.setVisible(false);
    StudentSession currentSession = StudentSession.getInstance();
    if (currentSession.isAuthenticated()) {
      ProposalDTO proposalDTO = requestStudentProposal(currentSession.getStudentData().getId());
      if (proposalDTO != null) {
        FileChooser fileChooser = new FileChooser();
        fileChooser
            .getExtensionFilters()
            .add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
          System.out.println("File: " + file.getAbsolutePath());
          boolean uploadSuccess = submitProposalFile(file, proposalDTO.getId());
        }
      } else {
        errorLabel1.setVisible(true);
      }
    } else {
      returnToLoginScene(event, currentSession);
    }
  }

  @FXML
  void chooseThesisFile(ActionEvent event) {
    errorLabel1.setVisible(false);
    errorLabel2.setVisible(false);
    StudentSession currentSession = StudentSession.getInstance();
    if (currentSession.isAuthenticated()) {
      ThesisDTO thesisDTO = requestStudentThesis(currentSession.getStudentData().getId());
      if (thesisDTO != null) {
        FileChooser fileChooser = new FileChooser();
        fileChooser
            .getExtensionFilters()
            .add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
          System.out.println("File: " + file.getAbsolutePath());
          boolean uploadSuccess = submitThesisFile(file, thesisDTO.getId());
        }
      } else {
        errorLabel2.setVisible(true);
      }
    } else {
      returnToLoginScene(event, currentSession);
    }
  }

  private boolean submitProposalFile(File file, Long proposalId) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
      body.add("file", new FileSystemResource(file));

      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
      ResponseEntity<?> responseEntity =
          new RestTemplate()
              .exchange(
                  "http://localhost:8080/api/proposal/" + proposalId + "/upload?file=" + file,
                  HttpMethod.POST,
                  requestEntity,
                  String.class);
      return responseEntity.getStatusCode().is2xxSuccessful();
    } catch (HttpClientErrorException e) {
      System.out.println("Error: " + e.getMessage());
      return false;
    }
  }

  private boolean submitThesisFile(File file, Long thesisId) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
      body.add("file", new FileSystemResource(file));

      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
      ResponseEntity<?> responseEntity =
          new RestTemplate()
              .exchange(
                  "http://localhost:8080/api/thesis/" + thesisId + "/upload?file=" + file,
                  HttpMethod.POST,
                  requestEntity,
                  String.class);
      return responseEntity.getStatusCode().is2xxSuccessful();
    } catch (HttpClientErrorException e) {
      System.out.println("Error: " + e.getMessage());
      return false;
    }
  }

  private ProposalDTO requestStudentProposal(Long studentId) {
    try {
      ResponseEntity<ProposalDTO> responseEntity =
          new RestTemplate()
              .exchange(
                  "http://localhost:8080/api/proposal?studentId=" + studentId,
                  HttpMethod.GET,
                  null,
                  new ParameterizedTypeReference<>() {});
      return responseEntity.getBody();
    } catch (HttpClientErrorException e) {
      System.out.println("Error: " + e.getMessage());
      return null;
    }
  }

  private ThesisDTO requestStudentThesis(Long studentId) {
    try {
      ResponseEntity<ThesisDTO> responseEntity =
          new RestTemplate()
              .exchange(
                  "http://localhost:8080/api/thesis?studentId=" + studentId,
                  HttpMethod.GET,
                  null,
                  new ParameterizedTypeReference<>() {});
      return responseEntity.getBody();
    } catch (HttpClientErrorException e) {
      System.out.println("Error: " + e.getMessage());
      return null;
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
