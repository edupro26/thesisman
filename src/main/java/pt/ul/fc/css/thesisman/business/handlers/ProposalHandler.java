package pt.ul.fc.css.thesisman.business.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.thesisman.business.entities.proposal.Proposal;
import pt.ul.fc.css.thesisman.business.services.ProposalService;

@Component
public class ProposalHandler {

    private final ProposalService proposalService;

    @Autowired
    public ProposalHandler(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    public Proposal getProposalById(Long id) {
        return proposalService.findById(id);
    }
}
