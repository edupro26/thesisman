package pt.ul.fc.css.thesisman.business.repositories.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.thesisman.business.entities.user.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

  @Query("SELECT t FROM Teacher t WHERE t.email = :q")
  Optional<Teacher> findByEmail(@Param("q") String q);
}
