package com.codehustle.rms.service.impl;

import com.codehustle.rms.repository.BranchRepository;
import com.codehustle.rms.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public Long getTotalBranchesCount(Long orgId) {
        return branchRepository.getTotalBranchesCount(orgId);
    }
}
