package com.course.restspring.pontointeligente.services;


import com.course.restspring.pontointeligente.entities.Employee;
import com.course.restspring.pontointeligente.entities.Release;
import com.course.restspring.pontointeligente.repositories.ReleaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReleaseService {

    private static final Logger log = LoggerFactory.getLogger(ReleaseService.class);

    @Autowired
    private ReleaseRepository releaseRepository;

    public Optional<Release> findById(Long id){
        log.info("Searching for a release by id {}", id);
        return releaseRepository.findById(id);
    }

    public List<Release> findByEmployeeId(Long id){
        log.info("Searching for a release by employee {}", id);
        return releaseRepository.findByEmployeeId(id);
    }

    public Release insertOnDB(Release release){
        log.info("Inserting release on DB {}", release);
        return releaseRepository.save(release);
    }

    public void deleteFromDB(Long id){
        log.info("Deleting release by id from DB {}", id);
        releaseRepository.deleteById(id);
    }
}
