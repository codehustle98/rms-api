package com.codehustle.rms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@JsonIgnoreProperties(value = {"createdBy","createdAt","modifiedBy","modifiedAt"},allowSetters = true)
public abstract class AuditableEntity {

    @Column(name = "created_by",nullable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "created_at",nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "modified_by",nullable = false)
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_at",nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
