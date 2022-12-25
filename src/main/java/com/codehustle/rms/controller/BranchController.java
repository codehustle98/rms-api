package com.codehustle.rms.controller;

import com.codehustle.rms.constants.MessageConstants;
import com.codehustle.rms.entity.Branch;
import com.codehustle.rms.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "branch")
@RestController
public class BranchController {

    @Autowired
    private BranchService branchService;
    @PostMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity addBranch(@RequestBody Branch branch){
        branchService.addBranch(branch);
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageConstants.BRANCH_ADD_SUCCESS);
    }
}
