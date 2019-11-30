package pl.grzegorz.repository;
import pl.grzegorz.domain.Nieruchomosc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Nieruchomosc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NieruchomoscRepository extends JpaRepository<Nieruchomosc, Long> {

}
