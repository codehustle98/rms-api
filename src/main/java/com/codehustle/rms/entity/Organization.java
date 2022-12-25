package com.codehustle.rms.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "organization")
@Data
public class Organization extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "seq",strategy = "increment")
    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "organization_code",nullable = false)
    private String organizationCode;

    @Column(name = "organization_name",nullable = false)
    private String organizationName;

}
