package pt.ul.fc.css.thesisman.business.repositories;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.thesisman.business.entities.defense.ProposalDefense;

public interface PDefenseRepository extends JpaRepository<ProposalDefense, Long> {

  @Query(
      "SELECT d FROM ProposalDefense d WHERE d.reservation.date > :startDate AND d.reservation.date < :endDate AND d.reservation.type = :type AND d.reservation.place = :place")
  List<ProposalDefense> findReservations(
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      @Param("type") String type,
      @Param("place") String place);

  @Query(
      "SELECT pd FROM ProposalDefense pd "
          + "JOIN pd.proposal p "
          + "WHERE p.internal.id = :internalTeacherId")
  List<ProposalDefense> findByInternalTeacher(@Param("internalTeacherId") Long internalTeacherId);

  @Query("SELECT COUNT(p) FROM ProposalDefense p WHERE p.grade >= 10")
  long countApprovedProposals();

   @Query("SELECT COUNT(p) FROM ProposalDefense p WHERE p.grade IS NOT NULL")
   long countGradedProposals();

}
