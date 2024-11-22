package pt.ul.fc.css.thesisman.presentation.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pt.ul.fc.css.thesisman.business.dtos.LoginForm;
import pt.ul.fc.css.thesisman.business.dtos.SignupForm;
import pt.ul.fc.css.thesisman.business.dtos.user.UserDTO;
import pt.ul.fc.css.thesisman.business.exceptions.UserAlreadyExistsException;
import pt.ul.fc.css.thesisman.business.handlers.LoginHandler;
import pt.ul.fc.css.thesisman.business.handlers.SessionHandler;

@Controller
public class HomeController {

  private final LoginHandler loginHandler;

  private final SessionHandler sessionHandler;

  @Autowired
  public HomeController(LoginHandler loginHandler, SessionHandler sessionHandler) {
    this.loginHandler = loginHandler;
    this.sessionHandler = sessionHandler;
  }

  @PostMapping("/login")
  public String login(Model model, @ModelAttribute LoginForm loginForm) {
    model.addAttribute("login", loginForm);
    if (loginHandler.login(loginForm)) return "redirect:/";
    model.addAttribute("login", new LoginForm());
    model.addAttribute("error", "Email ou password inválidos");
    return "login";
  }

  @PostMapping("/registo-empresa")
  public String registerCompanyUser(Model model, @ModelAttribute SignupForm signupForm) {
    model.addAttribute("signup", signupForm);
    try {
      if (loginHandler.createCompanyUser(signupForm)) {
        return "redirect:/login";
      }
      model.addAttribute("error", "Erro ao registar utizador");
      model.addAttribute("signup", new SignupForm());
      return "registo-empresa";
    } catch (UserAlreadyExistsException e) {
      model.addAttribute("error", e.getMessage());
      model.addAttribute("signup", new SignupForm());
      return "registo-empresa";
    }
  }

  @GetMapping("/registo-empresa")
  public String getSignup(Model model) {
    if (loginHandler.getSession().isAuthenticated()) return "redirect:/";
    model.addAttribute("signup", new SignupForm());
    return "registo-empresa";
  }

  @GetMapping("/login")
  public String getLogin(Model model) {
    if (loginHandler.getSession().isAuthenticated()) return "redirect:/";
    model.addAttribute("login", new LoginForm());
    return "login";
  }

  @GetMapping("/logout")
  public String logout() {
    if (!loginHandler.getSession().isAuthenticated()) return "redirect:/login";

    loginHandler.logout();
    return "redirect:/login";
  }

  @RequestMapping("/")
  public String getIndex(final Model model) {
    if (!loginHandler.getSession().isAuthenticated()) return "redirect:/login";
    UserDTO current = loginHandler.getSession().getCurrentUser();

    model.addAttribute("role", sessionHandler.isTeacher() ? "teacher" : "company");
    model.addAttribute("userEmail", current.getName());
    model.addAttribute("fragment", "home");
    return "index";
  }
}
