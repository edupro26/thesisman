package pt.ul.fc.css.thesisman.business.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ul.fc.css.thesisman.business.dtos.theme.ThemeDTO;
import pt.ul.fc.css.thesisman.business.handlers.ThemeApplicationHandler;

@RestController
@RequestMapping("/api")
public class ThemeController {

  private final ThemeApplicationHandler themeApplicationHandler;

  @Autowired
  public ThemeController(ThemeApplicationHandler themeApplicationHandler) {
    this.themeApplicationHandler = themeApplicationHandler;
  }

  @GetMapping("/themes")
  public List<ThemeDTO> getThemesByMastersAndYear(
      @RequestParam String masters, @RequestParam String year) {
    return themeApplicationHandler.findThemesByMastersAndYear(masters, year);
  }
}
