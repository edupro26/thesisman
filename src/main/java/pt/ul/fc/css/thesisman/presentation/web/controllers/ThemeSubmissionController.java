package pt.ul.fc.css.thesisman.presentation.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeSubmissionDTO;
import pt.ul.fc.css.thesisman.business.dtos.user.UserDTO;
import pt.ul.fc.css.thesisman.business.exceptions.ApplicationException;
import pt.ul.fc.css.thesisman.business.handlers.SessionHandler;
import pt.ul.fc.css.thesisman.business.handlers.ThemeSubmissionHandler;

@Controller
public class ThemeSubmissionController {

  private final SessionHandler sessionHandler;
  private final ThemeSubmissionHandler themeSubmissionHandler;

  @Autowired
  public ThemeSubmissionController(
      ThemeSubmissionHandler themeSubmissionHandler, SessionHandler sessionHandler) {
    this.themeSubmissionHandler = themeSubmissionHandler;
    this.sessionHandler = sessionHandler;
  }

  @GetMapping("/submissao-tema")
  public String getThemeSubmission(Model model) {
    if (!sessionHandler.isAuthenticated()) return "redirect:/login";

    // If the authenticated user is a student, logout and redirect to log in
    if (sessionHandler.isStudent()) {
      sessionHandler.deleteSession();
      return "redirect:/login";
    }

    UserDTO current = sessionHandler.getCurrentUser();
    model.addAttribute("userEmail", current.getName());
    model.addAttribute("role", sessionHandler.isTeacher() ? "teacher" : "company");
    model.addAttribute("submission", new ThemeSubmissionDTO());
    model.addAttribute("masters", themeSubmissionHandler.getMastersDegrees());
    model.addAttribute("teachers", themeSubmissionHandler.getTeachers());
    model.addAttribute("fragment", "theme-submit");
    return "index";
  }

  @PostMapping("/submeter-tema")
  public String submitTheme(
      Model model,
      @ModelAttribute ThemeSubmissionDTO submission,
      RedirectAttributes redirectAttributes) {
    try {
      if (!sessionHandler.isAuthenticated()) return "redirect:/login";
      // If the authenticated user is a student, logout and redirect to log in
      if (sessionHandler.isStudent()) {
        sessionHandler.deleteSession();
        return "redirect:/login";
      }

      model.addAttribute("submission", submission);
      UserDTO current = sessionHandler.getCurrentUser();
      try {
        themeSubmissionHandler.submitTheme(submission, current);
        return "redirect:/submissao-tema";
      } catch (ApplicationException e) {
        model.addAttribute("error", e.getMessage());
        model.addAttribute("userEmail", current.getName());
        model.addAttribute("role", sessionHandler.isTeacher() ? "teacher" : "company");
        model.addAttribute("submission", new ThemeSubmissionDTO());
        model.addAttribute("masters", themeSubmissionHandler.getMastersDegrees());
        model.addAttribute("teachers", themeSubmissionHandler.getTeachers());
        model.addAttribute("fragment", "theme-submit");
        return "index";
      }
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/submissao-tema";
    }
  }
}
