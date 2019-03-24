package manager.com.chitieu.service;

import manager.com.chitieu.domain.KiCongBo;
import manager.com.chitieu.repository.KiCongBoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing KiCongBo.
 */
@Service
@Transactional
public class KiCongBoService {

    private final Logger log = LoggerFactory.getLogger(KiCongBoService.class);

    private final KiCongBoRepository kiCongBoRepository;

    public KiCongBoService(KiCongBoRepository kiCongBoRepository) {
        this.kiCongBoRepository = kiCongBoRepository;
    }

    /**
     * Save a kiCongBo.
     *
     * @param kiCongBo the entity to save
     * @return the persisted entity
     */
    public KiCongBo save(KiCongBo kiCongBo) {
        log.debug("Request to save KiCongBo : {}", kiCongBo);
        return kiCongBoRepository.save(kiCongBo);
    }

    /**
     * Get all the kiCongBos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<KiCongBo> findAll(Pageable pageable) {
        log.debug("Request to get all KiCongBos");
        return kiCongBoRepository.findAll(pageable);
    }


    /**
     * Get one kiCongBo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<KiCongBo> findOne(Long id) {
        log.debug("Request to get KiCongBo : {}", id);
        return kiCongBoRepository.findById(id);
    }

    /**
     * Delete the kiCongBo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete KiCongBo : {}", id);
        kiCongBoRepository.deleteById(id);
    }
}
