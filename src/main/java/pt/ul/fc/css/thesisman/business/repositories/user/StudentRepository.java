package pt.ul.fc.css.thesisman.business.repositories.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.thesisman.business.entities.user.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

  @Query("SELECT s FROM Student s WHERE s.email = :q")
  Optional<Student> findByEmail(@Param("q") String q);

  @Query(
      "SELECT s FROM Student s "
          + "WHERE s.id NOT IN (SELECT p.student.id FROM Proposal p) "
          + "AND s IN (SELECT ta.student FROM ThemeApplication ta JOIN ta.themes t WHERE t.id = :themeId)")
  List<Student> findStudentsNotAssociatedWithAnyProposalAndWithTheme(
      @Param("themeId") Long themeId);
}
