package com.example.ex05.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @UpdateTimestamp
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
