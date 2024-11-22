package pt.ul.fc.css.thesisman.business.entities;

import jakarta.persistence.*;
import pt.ul.fc.css.thesisman.business.dtos.StatsDTO;

@Entity
@Table(name = "estatisticas")
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "total_propostas_avaliadas")
    private Long totalProposals;

    @Column(name = "propostas_aprovadas")
    private Long approvedProposals;

    @Column(name = "taxa_aprovacao_propostas")
    private Double proposalApprovalRate;

    @Column(name = "total_teses_avaliadas")
    private Long totalTheses;

    @Column(name = "teses_aprovadas")
    private Long approvedTheses;

    @Column(name = "taxa_aprovacao_teses")
    private Double thesisApprovalRate;

    public Stats() {}

    public Stats(Long totalProposals, Long approvedProposals, Long totalTheses, Long approvedTheses) {
        this.totalProposals = totalProposals;
        this.approvedProposals = approvedProposals;
        this.totalTheses = totalTheses;
        this.approvedTheses = approvedTheses;
        if (totalProposals == 0) {
            this.proposalApprovalRate = 0.0;
        } else {
            this.proposalApprovalRate = (double) approvedProposals / totalProposals * 100;
        }

        if (totalTheses == 0) {
            this.thesisApprovalRate = 0.0;
        } else {
            this.thesisApprovalRate = (double) approvedTheses / totalTheses * 100;
        }
    }

    public StatsDTO toDTO() {
        StatsDTO statsDTO = new StatsDTO();
        statsDTO.setTotalProposals(totalProposals);
        statsDTO.setTotalApprovedProposals(approvedProposals);
        statsDTO.setTotalTheses(totalTheses);
        statsDTO.setTotalApprovedTheses(approvedTheses);
        statsDTO.setProposalsApprovalRate(proposalApprovalRate);
        statsDTO.setThesesApprovalRate(thesisApprovalRate);
        return statsDTO;
    }
}
