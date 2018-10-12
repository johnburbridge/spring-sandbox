package org.burbridge.sandbox.api.domain

import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.EntityListeners
import org.joda.time.DateTime
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.CreatedBy
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class AuditableEntity {

    @Column(name = "created_by")
    @CreatedBy
    protected var createdBy: String? = null

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    protected var creationDate: DateTime? = null

    @Column(name = "last_modified_by")
    @LastModifiedBy
    protected var lastModifiedBy: String? = null

    @Column(name = "last_modified_date")
    @LastModifiedDate
    protected var lastModifiedDate: DateTime? = null
}