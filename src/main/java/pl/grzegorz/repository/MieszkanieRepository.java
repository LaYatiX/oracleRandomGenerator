package pl.grzegorz.repository;
import pl.grzegorz.domain.Mieszkanie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Mieszkanie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MieszkanieRepository extends JpaRepository<Mieszkanie, Long> {

}
