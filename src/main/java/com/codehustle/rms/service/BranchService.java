package com.codehustle.rms.service;

import com.codehustle.rms.entity.Branch;

public interface BranchService {

    Long getTotalBranchesCount(Long orgId);
    public void addBranch(Branch branch);
}
