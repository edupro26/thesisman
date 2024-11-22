package pt.ul.fc.css.thesisman.business.repositories.theme;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.thesisman.business.entities.theme.DissertationTheme;

public interface DissertationThemeRepository extends JpaRepository<DissertationTheme, Long> {

  @Query("SELECT t FROM DissertationTheme t WHERE t.internal.email = :e")
  List<DissertationTheme> findByInternalEmail(@Param("e") String email);

  @Query(
      "SELECT dt FROM DissertationTheme dt WHERE dt.id NOT IN (SELECT dp.theme.id FROM DissertationProposal dp)")
  List<DissertationTheme> findDissertationThemesNotAssociatedWithAnyProposal();
}
