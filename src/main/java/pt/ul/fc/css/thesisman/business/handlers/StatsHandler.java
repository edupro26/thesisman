package pt.ul.fc.css.thesisman.business.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.business.dtos.StatsDTO;
import pt.ul.fc.css.thesisman.business.services.StatsService;

@Component
public class StatsHandler {

    private final StatsService statsService;

    @Autowired
    public StatsHandler(StatsService statsService) {
        this.statsService = statsService;
    }

    public StatsDTO getStats() {
        return statsService.getStats();
    }
}
