package pt.ul.fc.css.thesisman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThesisManApplication {

  private static final Logger log = LoggerFactory.getLogger(ThesisManApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ThesisManApplication.class, args);
  }
}
