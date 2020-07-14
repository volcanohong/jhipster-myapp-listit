package com.hong.listit.web.rest;

import com.hong.listit.domain.Province;
import com.hong.listit.repository.ProvinceRepository;
import com.hong.listit.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hong.listit.domain.Province}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProvinceResource {

    private final Logger log = LoggerFactory.getLogger(ProvinceResource.class);

    private final ProvinceRepository provinceRepository;

    public ProvinceResource(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    /**
     * {@code GET  /provinces} : get all the provinces.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of provinces in body.
     */
    @GetMapping("/provinces")
    public List<Province> getAllProvinces() {
        log.debug("REST request to get all Provinces");
        return provinceRepository.findAll();
    }

    /**
     * {@code GET  /provinces/:id} : get the "id" province.
     *
     * @param id the id of the province to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the province, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/provinces/{id}")
    public ResponseEntity<Province> getProvince(@PathVariable Long id) {
        log.debug("REST request to get Province : {}", id);
        Optional<Province> province = provinceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(province);
    }
}
