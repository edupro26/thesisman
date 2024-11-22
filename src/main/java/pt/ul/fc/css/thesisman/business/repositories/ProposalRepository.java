package pt.ul.fc.css.thesisman.business.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.thesisman.business.entities.proposal.Proposal;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

  @Query(
      "SELECT p FROM Proposal p LEFT JOIN p.proposalDefense pd WHERE pd.grade IS NULL AND p.internal.id = :internal") // AND p.submitted = true
  List<Proposal> findProposalsWithoutDefenseGradeAndInternal(Long internal);

  @Query("SELECT p FROM Proposal p WHERE p.student.id = :i")
  Optional<Proposal> findByStudentId(@Param("i") Long id);
}
