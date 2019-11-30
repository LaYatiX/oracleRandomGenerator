package pl.grzegorz.repository;
import pl.grzegorz.domain.Lokalizacja;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lokalizacja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LokalizacjaRepository extends JpaRepository<Lokalizacja, Long> {

}
