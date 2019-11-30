package pl.grzegorz.repository;
import pl.grzegorz.domain.GodzinyOtwarcia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GodzinyOtwarcia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GodzinyOtwarciaRepository extends JpaRepository<GodzinyOtwarcia, Long> {

}
