package pt.ul.fc.css.thesisman.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.ul.fc.css.thesisman.business.controllers.UserController;
import pt.ul.fc.css.thesisman.business.dtos.LoginForm;
import pt.ul.fc.css.thesisman.business.entities.MastersDegree;
import pt.ul.fc.css.thesisman.business.entities.user.Student;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;
import pt.ul.fc.css.thesisman.business.exceptions.LoginFailedException;
import pt.ul.fc.css.thesisman.business.handlers.LoginHandler;

@WebMvcTest(UserController.class)
public class UserControllerTests {

  @Autowired private MockMvc mvc;

  @MockBean private LoginHandler loginHandler;

  @Autowired private ObjectMapper mapper;

  private Student mockStudent;

  @BeforeEach
  void setup() {
    Teacher teacher = new Teacher("Teacher1", "t1@email.com", "password");
    MastersDegree mockMastersDegree = new MastersDegree("LEI", teacher);
    mockStudent = new Student("Aluno1", "aluno1@alunos.fc.ul.pt", null, mockMastersDegree, 14.0);
  }

  @Test
  public void shouldLoginStudentWithSuccess() throws Exception {
    LoginForm login = new LoginForm();
    login.setEmail(mockStudent.getEmail());
    login.setPassword(mockStudent.getPassword());

    when(loginHandler.getStudentByEmail(login.getEmail())).thenReturn(mockStudent.toDTO());
    mvc.perform(
            post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(login)))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldFailToLoginStudent() throws Exception {
    LoginForm login = new LoginForm();
    login.setEmail("aluno2@alunos.fc.ul.pt");
    login.setPassword("password");

    when(loginHandler.getStudentByEmail(login.getEmail())).thenThrow(new LoginFailedException());
    mvc.perform(
            post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(login)))
        .andExpect(status().isUnauthorized());
  }
}
