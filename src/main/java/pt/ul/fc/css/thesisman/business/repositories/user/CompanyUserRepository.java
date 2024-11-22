package pt.ul.fc.css.thesisman.business.repositories.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.thesisman.business.entities.user.CompanyUser;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {

  @Query("SELECT c FROM CompanyUser c WHERE c.email = :q")
  Optional<CompanyUser> findByEmail(@Param("q") String q);
}
