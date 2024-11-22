package pt.ul.fc.css.thesisman.presentation.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ul.fc.css.thesisman.business.handlers.AssignGradeHandler;
import pt.ul.fc.css.thesisman.business.handlers.SessionHandler;

@Controller
public class AssignGradeController {

  private final SessionHandler sessionHandler;

  private final AssignGradeHandler assignGradeHandler;

  @Autowired
  public AssignGradeController(
      SessionHandler sessionHandler, AssignGradeHandler assignGradeHandler) {
    this.sessionHandler = sessionHandler;
    this.assignGradeHandler = assignGradeHandler;
  }

  @GetMapping("/atribuir-nota-propostas-defesa")
  public String getGradesToAssignDefenseProposals(Model model) {
    if (!sessionHandler.isAuthenticated()) return "redirect:/login";

    if (!sessionHandler.isTeacher()) {
      return "redirect:/";
    }

    model.addAttribute("userEmail", sessionHandler.getCurrentUser().getName());
    model.addAttribute("role", sessionHandler.isTeacher() ? "teacher" : "company");
    model.addAttribute("defenses", assignGradeHandler.getProposalsDefenses());
    model.addAttribute("type", "proposal");
    model.addAttribute("fragment", "grade-defenses");
    return "index";
  }

  @GetMapping("/atribuir-nota-teses-defesa")
  public String getGradesToAssignDefenseTheses(Model model) {
    if (!sessionHandler.isAuthenticated()) return "redirect:/login";

    if (!sessionHandler.isTeacher()) {
      return "redirect:/";
    }

    model.addAttribute("userEmail", sessionHandler.getCurrentUser().getName());
    model.addAttribute("role", sessionHandler.isTeacher() ? "teacher" : "company");
    model.addAttribute("defenses", assignGradeHandler.getThesesDefenses());
    model.addAttribute("type", "thesis");
    model.addAttribute("fragment", "grade-defenses");
    return "index";
  }

  @PostMapping("/atribuir-nota/{type}/{id}")
  public String assignGradeToDefense(
      @PathVariable String type,
      @PathVariable Long id,
      @RequestParam float grade,
      RedirectAttributes redirectAttributes) {
    try {
      if (!sessionHandler.isAuthenticated()) return "redirect:/login";

      if (!sessionHandler.isTeacher()) {
        return "redirect:/";
      }

      assignGradeHandler.assignGrade(id, grade);

      if (type.equals("proposta")) {
        return "redirect:/atribuir-nota-propostas-defesa";
      } else {
        return "redirect:/atribuir-nota-teses-defesa";
      }
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
      if (type.equals("proposta")) {
        return "redirect:/atribuir-nota-propostas-defesa";
      } else {
        return "redirect:/atribuir-nota-teses-defesa";
      }
    }
  }
}
