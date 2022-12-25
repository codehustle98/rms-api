package com.codehustle.rms.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "branch")
@Data
public class Branch extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "seq",strategy = "increment")
    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "org_id",nullable = false)
    private Long orgId;
}
