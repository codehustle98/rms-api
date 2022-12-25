package com.codehustle.rms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(value = {"userPassword"},allowSetters = true)
public class User extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "seq",strategy = "increment")
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name",nullable = false)
    private String name;

    @Column(name = "user_email",nullable = false)
    private String userEmailId;

    @Column(name = "user_password",nullable = false)
    private String userPassword;

    @Column(name = "user_type",length = 1,nullable = false)
    private String userType;

    @Type(type = "yes_no")
    @Column(name = "active",nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "org_id",referencedColumnName = "organization_id")
    private Organization organization;
}
