package com.course.restspring.pontointeligente.repositories;

import com.course.restspring.pontointeligente.entities.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

@Transactional(readOnly = true) //Doesn't make the DB stop, since it is a Read Only Operation
@NamedQueries({
    @NamedQuery(name = "ReleaseRepository.findByEmployeeId",
                query = "SELECT release FROM Release release WHERE release.employee.id = :employeeId") })

public interface ReleaseRepository extends JpaRepository<Release, Long> {

    List<Release> findByEmployeeId(@Param("employeeId") Long employeeId);

    Page<Release> findByEmployeeId(@Param("employeeId") Long employeeId, Pageable pageable);
}
