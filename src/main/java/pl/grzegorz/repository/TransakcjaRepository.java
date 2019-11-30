package pl.grzegorz.repository;
import pl.grzegorz.domain.Transakcja;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Transakcja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransakcjaRepository extends JpaRepository<Transakcja, Long> {

}
