package pt.ul.fc.css.thesisman.business.repositories.theme;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.thesisman.business.entities.theme.ProjectTheme;

public interface ProjectThemeRepository extends JpaRepository<ProjectTheme, Long> {

  @Query("SELECT t FROM ProjectTheme t WHERE t.internal.email = :e")
  List<ProjectTheme> findByInternalEmail(@Param("e") String email);

  @Query("SELECT t FROM ProjectTheme t WHERE t.external.email = :e")
  List<ProjectTheme> findByExternalEmail(@Param("e") String email);

  @Query(
      "SELECT pt FROM ProjectTheme pt WHERE pt.id NOT IN (SELECT pp.theme.id FROM ProjectProposal pp)")
  List<ProjectTheme> findProjectThemesNotAssociatedWithAnyProposal();
}
