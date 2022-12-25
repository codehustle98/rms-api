package com.codehustle.rms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "branch")
@RestController
public class BranchController {

    @PostMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addBranch(){}
}
