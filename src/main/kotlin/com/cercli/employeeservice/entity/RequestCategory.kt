package com.cercli.employeeservice.entity

import com.cercli.employeeservice.dto.RequestCategoryDto
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "request_category")
data class RequestCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val requestCategoryId: UUID? = null,
    @Column(name = "name", nullable = false)
    val name: String
) {
    constructor() : this(requestCategoryId = UUID.randomUUID(), name = "")

    fun toDto(): RequestCategoryDto {
        return RequestCategoryDto(
            requestCategoryId = this.requestCategoryId,
            name = this.name
        )
    }
}