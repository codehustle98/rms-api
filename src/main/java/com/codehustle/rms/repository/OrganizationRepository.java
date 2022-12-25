package com.codehustle.rms.repository;

import com.codehustle.rms.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization,Long> {

    Organization findByOrganizationCode(String orgCode);
}
