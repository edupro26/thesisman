package pt.ul.fc.css.thesisman.presentation.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ul.fc.css.thesisman.business.handlers.SessionHandler;
import pt.ul.fc.css.thesisman.business.handlers.ThemeAssignHandler;

@Controller
public class AssignThemeController {

  private final SessionHandler sessionHandler;

  private final ThemeAssignHandler themeAssignHandler;

  @Autowired
  public AssignThemeController(
      SessionHandler sessionHandler, ThemeAssignHandler themeAssignHandler) {
    this.themeAssignHandler = themeAssignHandler;
    this.sessionHandler = sessionHandler;
  }

  @GetMapping("/atribuir-tema")
  public String getAssignThemePage(Model model) {
    if (!sessionHandler.isAuthenticated()) return "redirect:/login";
    if (!sessionHandler.isTeacher()) return "redirect:/";

    model.addAttribute("userEmail", sessionHandler.getCurrentUser().getName());
    model.addAttribute("role", sessionHandler.isTeacher() ? "teacher" : "company");
    model.addAttribute("themes", themeAssignHandler.getNotAssignedThemes());
    model.addAttribute("fragment", "themes-to-assign");
    return "index";
  }

  @GetMapping("/atribuir-tema/{id}")
  public String getAssignThemePageWithId(@PathVariable("id") Long id, Model model) {
    if (!sessionHandler.isAuthenticated()) return "redirect:/login";

    if (!sessionHandler.isTeacher()) return "redirect:/";

    model.addAttribute("userEmail", sessionHandler.getCurrentUser().getName());
    model.addAttribute("role", sessionHandler.isTeacher() ? "teacher" : "company");
    model.addAttribute("themeId", id);
    model.addAttribute("students", themeAssignHandler.getApplicantsForTheme(id));
    model.addAttribute("fragment", "assign-theme-to-student");
    return "index";
  }

  @PostMapping("/atribuir-tema/{id}")
  public String assignTheme(
      @PathVariable("id") Long id,
      @RequestParam Long studentId,
      RedirectAttributes redirectAttributes) {
    try {
      if (!sessionHandler.isAuthenticated()) return "redirect:/login";

      if (!sessionHandler.isTeacher()) return "redirect:/";

      themeAssignHandler.assignThemeToStudent(studentId, id);

      return "redirect:/atribuir-tema";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/atribuir-tema/" + id;
    }
  }
}
