package pt.ul.fc.css.thesisman.business.dtos;

public class StatsDTO {

        private long totalProposals;
        private long totalApprovedProposals;
        private long totalTheses;
        private long totalApprovedTheses;

        private double thesesApprovalRate;
        private double proposalsApprovalRate;

        public long getTotalProposals() {
            return totalProposals;
        }

        public void setTotalProposals(long totalProposals) {
            this.totalProposals = totalProposals;
        }

        public long getTotalApprovedProposals() {
            return totalApprovedProposals;
        }

        public void setTotalApprovedProposals(long totalApprovedProposals) {
            this.totalApprovedProposals = totalApprovedProposals;
        }

        public long getTotalTheses() {
            return totalTheses;
        }

        public void setTotalTheses(long totalTheses) {
            this.totalTheses = totalTheses;
        }

        public long getTotalApprovedTheses() {
            return totalApprovedTheses;
        }

        public void setTotalApprovedTheses(long totalApprovedTheses) {
            this.totalApprovedTheses = totalApprovedTheses;
        }

        public double getThesesApprovalRate() {
            return thesesApprovalRate;
        }

        public void setThesesApprovalRate(double thesesApprovalRate) {
            this.thesesApprovalRate = thesesApprovalRate;
        }

        public double getProposalsApprovalRate() {
            return proposalsApprovalRate;
        }

        public void setProposalsApprovalRate(double proposalsApprovalRate) {
            this.proposalsApprovalRate = proposalsApprovalRate;
        }
}
