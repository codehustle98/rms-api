package com.codehustle.rms.controller;

import com.codehustle.rms.entity.User;
import com.codehustle.rms.model.Dashboard;
import com.codehustle.rms.security.SecurityUtils;
import com.codehustle.rms.service.BranchService;
import com.codehustle.rms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/dashboard")
@RestController
public class DashboardController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Dashboard getDashboardData(){
        Dashboard dashboard = new Dashboard();
        User user = userService.findUserbyId(SecurityUtils.getUserId());
        if(user != null){
            dashboard.setTotalBranches(branchService.getTotalBranchesCount(user.getOrganization().getOrganizationId()));
        }
        return dashboard;
    }
}
