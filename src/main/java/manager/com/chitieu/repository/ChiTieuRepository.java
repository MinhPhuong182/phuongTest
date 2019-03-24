package manager.com.chitieu.repository;

import manager.com.chitieu.domain.ChiTieu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ChiTieu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChiTieuRepository extends JpaRepository<ChiTieu, Long> {

}
