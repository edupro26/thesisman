package pt.ul.fc.css.thesisman.business.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.thesisman.business.entities.MastersDegree;
import pt.ul.fc.css.thesisman.business.entities.theme.Theme;

public interface MastersDegreeRepository extends JpaRepository<MastersDegree, Long> {

  @Query("SELECT m FROM MastersDegree m WHERE m.name = :q")
  Optional<MastersDegree> findByName(@Param("q") String name);

  @Query("SELECT m FROM MastersDegree m WHERE m.coordinator.name = :n")
  Optional<MastersDegree> findByCoordinatorName(@Param("n") String name);

  @Query("SELECT DISTINCT t FROM Theme t JOIN t.masters m WHERE m.name = :n")
  List<Theme> findThemesByMastersName(@Param("n") String name);
}
