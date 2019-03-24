package manager.com.chitieu.repository;

import manager.com.chitieu.domain.NhomChiTieu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NhomChiTieu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhomChiTieuRepository extends JpaRepository<NhomChiTieu, Long> {

}
