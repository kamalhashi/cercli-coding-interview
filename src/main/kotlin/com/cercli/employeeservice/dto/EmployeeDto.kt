package com.cercli.employeeservice.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime
import java.util.UUID


data class CreateEmployeeDto(
    val name: String,
    val position: String,
    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    val email: String,
    val salary: Float
)

data class UpdateEmployeeDto(
    val name: String,
    val position: String,
    val email: String,
    val salary: Float
)

data class EmployeeDto(
    val employeeId: UUID?,
    val name: String,
    val position: String,
    val email: String,
    val salary: Float,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime
)