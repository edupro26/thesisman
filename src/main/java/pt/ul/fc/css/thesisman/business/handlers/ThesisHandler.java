package pt.ul.fc.css.thesisman.business.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.business.entities.thesis.Thesis;
import pt.ul.fc.css.thesisman.business.services.ThesisService;

@Component
public class ThesisHandler {

    private final ThesisService thesisService;

    @Autowired
    public ThesisHandler(ThesisService thesisService) {
        this.thesisService = thesisService;
    }

    public Thesis getThesisById(Long id) {
        return thesisService.findById(id);
    }
}
