package pt.ul.fc.css.thesisman.business.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.thesisman.business.entities.ThemeApplication;

public interface ApplicationRepository extends JpaRepository<ThemeApplication, Long> {

  @Query("SELECT a FROM ThemeApplication a WHERE a.student.id = :i")
  Optional<ThemeApplication> findByStudentId(@Param("i") Long id);
}
