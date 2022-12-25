package com.codehustle.rms.repository;

import com.codehustle.rms.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface BranchRepository extends JpaRepository<Branch,Long> {

    @Query("select count (b) from Branch b where b.orgId =:orgId ")
    Long getTotalBranchesCount(@Param("orgId")Long orgId);
}
