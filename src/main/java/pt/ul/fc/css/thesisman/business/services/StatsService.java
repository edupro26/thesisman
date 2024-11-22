package pt.ul.fc.css.thesisman.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.business.dtos.StatsDTO;
import pt.ul.fc.css.thesisman.business.entities.Stats;
import pt.ul.fc.css.thesisman.business.repositories.PDefenseRepository;
import pt.ul.fc.css.thesisman.business.repositories.TDefenseRepository;
import pt.ul.fc.css.thesisman.business.repositories.StatsRepository;

import java.util.List;

@Service
public class StatsService {

    private final StatsRepository statsRepository;
    private final PDefenseRepository pDefenseRepository;
    private final TDefenseRepository tDefenseRepository;

    @Autowired
    public StatsService(StatsRepository statsRepository, PDefenseRepository pDefenseRepository, TDefenseRepository tDefenseRepository) {
        this.statsRepository = statsRepository;
        this.pDefenseRepository = pDefenseRepository;
        this.tDefenseRepository = tDefenseRepository;
    }

    public Stats updateStats() {
        long totalProposals = pDefenseRepository.countGradedProposals();
        long totalApprovedProposals = pDefenseRepository.countApprovedProposals();
        long totalTheses = tDefenseRepository.countGradedTheses();
        long totalApprovedTheses = tDefenseRepository.countApprovedTheses();

        statsRepository.deleteAll();

        Stats stats = new Stats(totalProposals, totalApprovedProposals, totalTheses, totalApprovedTheses);

        return statsRepository.save(stats);
    }

    public StatsDTO getStats() {
        List<Stats> stats = statsRepository.findAll();
        if (stats.isEmpty()) {
            return updateStats().toDTO();
        }
        return stats.get(0).toDTO();
    }
}
