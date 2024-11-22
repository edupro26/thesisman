package pt.ul.fc.css.thesisman.business.repositories;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.thesisman.business.entities.defense.ThesisDefense;

public interface TDefenseRepository extends JpaRepository<ThesisDefense, Long> {

  @Query(
      "SELECT d FROM ThesisDefense d WHERE d.reservation.date > :startDate AND d.reservation.date < :endDate AND d.reservation.type = :type AND d.reservation.place = :place")
  List<ThesisDefense> findReservations(
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate,
      @Param("type") String type,
      @Param("place") String place);

  @Query(
      "SELECT td FROM ThesisDefense td "
          + "JOIN td.jury j "
          + "WHERE j.president.id = :presidentId")
  List<ThesisDefense> findByPresident(@Param("presidentId") Long presidentId);

  @Query("SELECT COUNT(t) FROM ThesisDefense t WHERE t.grade >= 10")
  long countApprovedTheses();


  @Query("SELECT COUNT(t) FROM ThesisDefense t WHERE t.grade IS NOT NULL")
  long countGradedTheses();

}
