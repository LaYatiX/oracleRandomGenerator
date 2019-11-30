package pl.grzegorz.repository;
import pl.grzegorz.domain.Sklep;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sklep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SklepRepository extends JpaRepository<Sklep, Long> {

}
