package pt.ul.fc.css.thesisman.business.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.thesisman.business.entities.thesis.Thesis;

public interface ThesisRepository extends JpaRepository<Thesis, Long> {

  @Query(
      "SELECT t FROM Thesis t LEFT JOIN t.thesisDefense td WHERE td.grade IS NULL AND t.internal.id = :internal") //  AND t.submitted = true
  List<Thesis> findThesesWithoutDefenseGradeAndInternal(Long internal);

  @Query("SELECT t FROM Thesis t WHERE t.student.id = :i")
  Optional<Thesis> findByStudentId(@Param("i") Long id);
}
