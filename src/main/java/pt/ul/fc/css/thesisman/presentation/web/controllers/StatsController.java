package pt.ul.fc.css.thesisman.presentation.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ul.fc.css.thesisman.business.handlers.SessionHandler;
import pt.ul.fc.css.thesisman.business.handlers.StatsHandler;

@Controller
public class StatsController {

    private final StatsHandler statsHandler;
    private final SessionHandler sessionHandler;

    public StatsController(StatsHandler statsHandler, SessionHandler sessionHandler) {
        this.statsHandler = statsHandler;
        this.sessionHandler = sessionHandler;
    }

    @GetMapping("/estatisticas")
    public String stats(Model model) {
        if (!sessionHandler.isAuthenticated()) return "redirect:/login";

        if (!sessionHandler.isTeacher()) return "redirect:/";

        model.addAttribute("userEmail", sessionHandler.getCurrentUser().getName());
        model.addAttribute("role", sessionHandler.isTeacher() ? "teacher" : "company");
        model.addAttribute("stats", statsHandler.getStats());
        model.addAttribute("fragment", "stats");
        return "index";
    }
}
