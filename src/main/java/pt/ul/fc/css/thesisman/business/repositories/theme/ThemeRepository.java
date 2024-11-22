package pt.ul.fc.css.thesisman.business.repositories.theme;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.thesisman.business.entities.MastersDegree;
import pt.ul.fc.css.thesisman.business.entities.SchoolYear;
import pt.ul.fc.css.thesisman.business.entities.theme.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

  @Query("SELECT t FROM Theme t WHERE t.title = :t AND t.schoolYear = :y")
  Optional<Theme> findByTitleAndYear(@Param("t") String title, @Param("y") SchoolYear year);

  @Query("SELECT t FROM Theme t JOIN t.masters m WHERE m.name = :m AND t.schoolYear.year = :y")
  List<Theme> findByMastersAndYear(@Param("m") String name, @Param("y") String year);

  @Query("SELECT t FROM Theme t WHERE t.schoolYear = :y")
  List<Theme> findByYear(@Param("y") SchoolYear year);

  @Query(
      "SELECT t FROM Theme t "
          + "LEFT JOIN ProjectProposal pp ON t.id = pp.theme.id "
          + "LEFT JOIN DissertationProposal dp ON t.id = dp.theme.id "
          + "WHERE t.schoolYear = :schoolYear "
          + "AND :mastersDegree MEMBER OF t.masters "
          + "AND pp.id IS NULL "
          + "AND dp.id IS NULL")
  List<Theme> findThemesWithoutProposalsAndWithMastersDegreeAndSchoolYear(
      @Param("schoolYear") SchoolYear schoolYear,
      @Param("mastersDegree") MastersDegree mastersDegree);
}
