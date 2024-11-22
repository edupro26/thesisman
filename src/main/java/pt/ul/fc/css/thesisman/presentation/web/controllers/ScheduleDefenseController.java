package pt.ul.fc.css.thesisman.presentation.web.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.ul.fc.css.thesisman.business.handlers.ScheduleDefenseHandler;
import pt.ul.fc.css.thesisman.business.handlers.SessionHandler;

@Controller
public class ScheduleDefenseController {

  private final SessionHandler sessionHandler;
  private final ScheduleDefenseHandler scheduleDefenseHandler;

  @Autowired
  public ScheduleDefenseController(
      ScheduleDefenseHandler scheduleDefenseHandler, SessionHandler sessionHandler) {
    this.scheduleDefenseHandler = scheduleDefenseHandler;
    this.sessionHandler = sessionHandler;
  }

  @GetMapping("marcar-defesa-tese")
  public String thesesToSchedule(Model model) {
    if (!sessionHandler.isAuthenticated()) return "redirect:/login";

    if (!sessionHandler.isTeacher()) {
      return "redirect:/";
    }

    model.addAttribute("userEmail", sessionHandler.getCurrentUser().getName());
    model.addAttribute("role", sessionHandler.isTeacher() ? "teacher" : "company");
    model.addAttribute("theses", scheduleDefenseHandler.getTheses());
    model.addAttribute("fragment", "defenses-thesis");
    return "index";
  }

  @GetMapping("marcar-defesa-proposta")
  public String proposalsToSchedule(Model model) {
    if (!sessionHandler.isAuthenticated()) return "redirect:/login";

    if (!sessionHandler.isTeacher()) {
      return "redirect:/";
    }

    model.addAttribute("userEmail", sessionHandler.getCurrentUser().getName());
    model.addAttribute("role", sessionHandler.isTeacher() ? "teacher" : "company");
    model.addAttribute("proposals", scheduleDefenseHandler.getProposals());
    model.addAttribute("fragment", "defenses-proposal");
    return "index";
  }

  @GetMapping("marcar-defesa-tese/{id}")
  public String scheduleThesisDefensePage(@PathVariable Long id, Model model) {
    if (!sessionHandler.isAuthenticated()) return "redirect:/login";

    if (!sessionHandler.isTeacher()) {
      return "redirect:/";
    }

    model.addAttribute("userEmail", sessionHandler.getCurrentUser().getName());
    model.addAttribute("role", sessionHandler.isTeacher() ? "teacher" : "company");
    model.addAttribute("id", id);
    model.addAttribute("type", "thesis");
    model.addAttribute("teachers", scheduleDefenseHandler.getTeachers());
    model.addAttribute("rooms", scheduleDefenseHandler.getRooms());
    model.addAttribute("fragment", "schedule-defense");
    return "index";
  }

  @GetMapping("marcar-defesa-proposta/{id}")
  public String scheduleProposalDefensePage(@PathVariable Long id, Model model) {
    if (!sessionHandler.isAuthenticated()) return "redirect:/login";

    if (!sessionHandler.isTeacher()) {
      return "redirect:/";
    }

    model.addAttribute("userEmail", sessionHandler.getCurrentUser().getName());
    model.addAttribute("role", sessionHandler.isTeacher() ? "teacher" : "company");
    model.addAttribute("id", id);
    model.addAttribute("type", "proposal");
    model.addAttribute("rooms", scheduleDefenseHandler.getRooms());
    model.addAttribute("fragment", "schedule-defense");
    return "index";
  }

  @PostMapping("marcar-defesa-tese/{id}")
  public String scheduleThesisDefense(
      @PathVariable("id") Long id,
      @RequestParam("date") String dateString,
      @RequestParam("defenseType") String defenseType,
      @RequestParam(value = "place", required = false) String place,
      @RequestParam(value = "url", required = false) String url,
      @RequestParam("arguer") Long arguenteId,
      @RequestParam("president") Long presidenteId,
      RedirectAttributes redirectAttributes) {
    try {
      if (!sessionHandler.isAuthenticated()) return "redirect:/login";

      if (!sessionHandler.isTeacher()) {
        return "redirect:/";
      }
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
      LocalDateTime date = LocalDateTime.parse(dateString, formatter);

      if (defenseType.equals("remote")) {
        scheduleDefenseHandler.scheduleThesisDefense(
            id, date, defenseType, url, arguenteId, presidenteId);
      } else {
        scheduleDefenseHandler.scheduleThesisDefense(
            id, date, defenseType, place, arguenteId, presidenteId);
      }
      return "redirect:/marcar-defesa-tese";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
      return "redirect:/marcar-defesa-tese";
    }
  }

  @PostMapping("marcar-defesa-proposta/{id}")
  public String scheduleProposalDefense(
      @PathVariable("id") Long id,
      @RequestParam("date") String dateString,
      @RequestParam("defenseType") String defenseType,
      @RequestParam(value = "place", required = false) String place,
      @RequestParam(value = "url", required = false) String url,
      RedirectAttributes redirectAttributes) {
    try {
      if (!sessionHandler.isAuthenticated()) return "redirect:/login";

      if (!sessionHandler.isTeacher()) {
        return "redirect:/";
      }
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
      LocalDateTime date = LocalDateTime.parse(dateString, formatter);

      if (defenseType.equals("remote")) {
        scheduleDefenseHandler.scheduleProposalDefense(id, date, defenseType, url);
      } else {
        scheduleDefenseHandler.scheduleProposalDefense(id, date, defenseType, place);
      }
      return "redirect:/marcar-defesa-proposta";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
      return "redirect:/marcar-defesa-proposta";
    }
  }
}
