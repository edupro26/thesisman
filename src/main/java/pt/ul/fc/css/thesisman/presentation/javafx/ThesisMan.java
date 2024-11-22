package pt.ul.fc.css.thesisman.presentation.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ThesisMan extends Application {

  private static final String VIEWS = "/javafx/views/";

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS + "login.fxml"));
    Scene scene = new Scene(loader.load());
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.setTitle("ThesisMan | Login");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
