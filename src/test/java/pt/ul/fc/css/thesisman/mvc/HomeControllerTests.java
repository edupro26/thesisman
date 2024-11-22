package pt.ul.fc.css.thesisman.mvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pt.ul.fc.css.thesisman.business.dtos.LoginForm;
import pt.ul.fc.css.thesisman.business.dtos.SignupForm;
import pt.ul.fc.css.thesisman.business.exceptions.UserAlreadyExistsException;
import pt.ul.fc.css.thesisman.business.handlers.LoginHandler;
import pt.ul.fc.css.thesisman.business.handlers.SessionHandler;
import pt.ul.fc.css.thesisman.presentation.web.controllers.HomeController;

@WebMvcTest(controllers = HomeController.class)
public class HomeControllerTests {

  @Autowired private MockMvc mvc;

  @MockBean private LoginHandler loginHandler;

  @MockBean private SessionHandler sessionHandler;

  @Test
  void shouldRedirectToLogin() throws Exception {
    when(loginHandler.getSession()).thenReturn(sessionHandler);
    when(!sessionHandler.isAuthenticated()).thenReturn(false);
    mvc.perform(get("/")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
  }

  @Test
  void shouldGetLogin() throws Exception {
    when(loginHandler.getSession()).thenReturn(sessionHandler);
    when(sessionHandler.isAuthenticated()).thenReturn(false);
    mvc.perform(get("/login"))
        .andExpect(status().isOk())
        .andExpect(view().name("login"))
        .andExpect(model().attributeExists("login"));
  }

  @Test
  void shouldGetSignup() throws Exception {
    when(loginHandler.getSession()).thenReturn(sessionHandler);
    when(sessionHandler.isAuthenticated()).thenReturn(false);
    mvc.perform(get("/registo-empresa"))
        .andExpect(status().isOk())
        .andExpect(view().name("registo-empresa"))
        .andExpect(model().attributeExists("signup"));
  }

  @Test
  void shouldLoginTeacher() throws Exception {
    LoginForm loginForm = new LoginForm();
    loginForm.setEmail("professor1@ciencias.ulisboa.pt");
    loginForm.setPassword("password");

    when(loginHandler.login(loginForm)).thenReturn(true);
    mvc.perform(post("/login").flashAttr("loginForm", loginForm))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"));
  }

  @Test
  void shouldLoginCompanyUser() throws Exception {
    LoginForm loginForm = new LoginForm();
    loginForm.setEmail("empresarial@empresa.pt");
    loginForm.setPassword("password");

    when(loginHandler.login(loginForm)).thenReturn(true);
    mvc.perform(post("/login").flashAttr("loginForm", loginForm))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"));
  }

  @Test
  void shouldFailLogin() throws Exception {
    LoginForm loginForm = new LoginForm();
    loginForm.setEmail("invalid@empresa.pt");
    loginForm.setPassword("password");

    when(loginHandler.login(loginForm)).thenReturn(false);
    mvc.perform(post("/login").flashAttr("loginForm", loginForm))
        .andExpect(status().isOk())
        .andExpect(view().name("login"))
        .andExpect(model().attributeExists("login"));
  }

  @Test
  void shouldRegisterCompanyUser() throws Exception {
    SignupForm signupForm = new SignupForm();
    signupForm.setEmail("empresarial@empresa.pt");
    signupForm.setPassword("password");

    when(loginHandler.createCompanyUser(signupForm)).thenReturn(true);
    mvc.perform(post("/registo-empresa").flashAttr("signupForm", signupForm))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/login"));
  }

  @Test
  void shouldThrowAlreadyExistsException() throws Exception {
    SignupForm signupForm = new SignupForm();
    signupForm.setEmail("frederico.nunes@sonae.pt");
    signupForm.setPassword("password");

    when(loginHandler.createCompanyUser(signupForm)).thenReturn(false);
    when(loginHandler.createCompanyUser(signupForm)).thenThrow(new UserAlreadyExistsException());
    mvc.perform(post("/registo-empresa").flashAttr("signupForm", signupForm))
        .andExpect(status().isOk())
        .andExpect(view().name("registo-empresa"))
        .andExpect(model().attributeExists("signup"));
  }

  @Test
  void shouldLogout() throws Exception {
    when(loginHandler.getSession()).thenReturn(sessionHandler);
    when(!sessionHandler.isAuthenticated()).thenReturn(false);
    mvc.perform(get("/logout"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/login"));
  }
}
