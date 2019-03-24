package manager.com.chitieu.web.rest;
import manager.com.chitieu.domain.KiCongBo;
import manager.com.chitieu.service.KiCongBoService;
import manager.com.chitieu.web.rest.errors.BadRequestAlertException;
import manager.com.chitieu.web.rest.util.HeaderUtil;
import manager.com.chitieu.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing KiCongBo.
 */
@RestController
@RequestMapping("/api")
public class KiCongBoResource {

    private final Logger log = LoggerFactory.getLogger(KiCongBoResource.class);

    private static final String ENTITY_NAME = "quanlychitieuKiCongBo";

    private final KiCongBoService kiCongBoService;

    public KiCongBoResource(KiCongBoService kiCongBoService) {
        this.kiCongBoService = kiCongBoService;
    }

    /**
     * POST  /ki-cong-bos : Create a new kiCongBo.
     *
     * @param kiCongBo the kiCongBo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kiCongBo, or with status 400 (Bad Request) if the kiCongBo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ki-cong-bos")
    public ResponseEntity<KiCongBo> createKiCongBo(@Valid @RequestBody KiCongBo kiCongBo) throws URISyntaxException {
        log.debug("REST request to save KiCongBo : {}", kiCongBo);
        if (kiCongBo.getId() != null) {
            throw new BadRequestAlertException("A new kiCongBo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KiCongBo result = kiCongBoService.save(kiCongBo);
        return ResponseEntity.created(new URI("/api/ki-cong-bos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ki-cong-bos : Updates an existing kiCongBo.
     *
     * @param kiCongBo the kiCongBo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kiCongBo,
     * or with status 400 (Bad Request) if the kiCongBo is not valid,
     * or with status 500 (Internal Server Error) if the kiCongBo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ki-cong-bos")
    public ResponseEntity<KiCongBo> updateKiCongBo(@Valid @RequestBody KiCongBo kiCongBo) throws URISyntaxException {
        log.debug("REST request to update KiCongBo : {}", kiCongBo);
        if (kiCongBo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KiCongBo result = kiCongBoService.save(kiCongBo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kiCongBo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ki-cong-bos : get all the kiCongBos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of kiCongBos in body
     */
    @GetMapping("/ki-cong-bos")
    public ResponseEntity<List<KiCongBo>> getAllKiCongBos(Pageable pageable) {
        log.debug("REST request to get a page of KiCongBos");
        Page<KiCongBo> page = kiCongBoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ki-cong-bos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /ki-cong-bos/:id : get the "id" kiCongBo.
     *
     * @param id the id of the kiCongBo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kiCongBo, or with status 404 (Not Found)
     */
    @GetMapping("/ki-cong-bos/{id}")
    public ResponseEntity<KiCongBo> getKiCongBo(@PathVariable Long id) {
        log.debug("REST request to get KiCongBo : {}", id);
        Optional<KiCongBo> kiCongBo = kiCongBoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kiCongBo);
    }

    /**
     * DELETE  /ki-cong-bos/:id : delete the "id" kiCongBo.
     *
     * @param id the id of the kiCongBo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ki-cong-bos/{id}")
    public ResponseEntity<Void> deleteKiCongBo(@PathVariable Long id) {
        log.debug("REST request to delete KiCongBo : {}", id);
        kiCongBoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
