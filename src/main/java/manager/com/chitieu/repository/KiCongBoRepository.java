package manager.com.chitieu.repository;

import manager.com.chitieu.domain.KiCongBo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the KiCongBo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KiCongBoRepository extends JpaRepository<KiCongBo, Long> {

}
