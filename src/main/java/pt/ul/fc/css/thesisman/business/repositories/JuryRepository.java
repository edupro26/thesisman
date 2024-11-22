package pt.ul.fc.css.thesisman.business.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ul.fc.css.thesisman.business.entities.defense.Jury;

public interface JuryRepository extends JpaRepository<Jury, Long> {}
