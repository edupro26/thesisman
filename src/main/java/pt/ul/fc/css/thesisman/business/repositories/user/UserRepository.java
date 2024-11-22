package pt.ul.fc.css.thesisman.business.repositories.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.ul.fc.css.thesisman.business.entities.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.email = :e")
  Optional<User> findByEmail(@Param("e") String email);
}
