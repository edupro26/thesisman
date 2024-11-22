package pt.ul.fc.css.thesisman.business.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ul.fc.css.thesisman.business.entities.defense.ProposalDefense;
import pt.ul.fc.css.thesisman.business.entities.defense.location.Reservation;
import pt.ul.fc.css.thesisman.business.entities.proposal.Proposal;
import pt.ul.fc.css.thesisman.business.repositories.PDefenseRepository;
import pt.ul.fc.css.thesisman.business.repositories.ProposalRepository;

@Service
public class PDefenseService {

  private final PDefenseRepository pDefenseRepository;
  private final ProposalRepository proposalRepository;

  private final int PROPOSAL_DURATION = 60;

  @Autowired
  public PDefenseService(
      PDefenseRepository pDefenseRepository, ProposalRepository proposalRepository) {
    this.pDefenseRepository = pDefenseRepository;
    this.proposalRepository = proposalRepository;
  }

  public List<ProposalDefense> findRoomReservations(
      LocalDateTime startDate, LocalDateTime endDate, String type, String place) {
    LocalDateTime startDateMax = startDate.minusMinutes(PROPOSAL_DURATION);
    return pDefenseRepository.findReservations(startDateMax, endDate, type, place);
  }

  public Optional<ProposalDefense> findById(Long id) {
    return pDefenseRepository.findById(id);
  }

  public void createDefense(Reservation reservation, Proposal proposal) {
    ProposalDefense proposalDefense = new ProposalDefense();
    proposalDefense.setReservation(reservation);
    proposalDefense.setProposal(proposal);
    pDefenseRepository.save(proposalDefense);
    proposal.setDefense(proposalDefense);
    proposalRepository.save(proposal);
  }
}
