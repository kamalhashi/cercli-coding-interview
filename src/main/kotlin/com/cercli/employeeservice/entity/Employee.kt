package com.cercli.employeeservice.entity

import com.cercli.employeeservice.dto.CreateEmployeeDto
import com.cercli.employeeservice.dto.EmployeeDto
import com.cercli.employeeservice.dto.UpdateEmployeeDto
import com.cercli.employeeservice.enums.TimeZoneEnum
import com.cercli.employeeservice.util.DateTimeUtils
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "employees")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val employeeId: UUID? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "position", nullable = false)
    val position: String,

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "salary", nullable = false)
    val salary: Float,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = DateTimeUtils.getCurrentDateTimeInTimezone(TimeZoneEnum.AMERICA_NEW_YORK),

    @Column(name = "modified_at", nullable = false)
    val modifiedAt: LocalDateTime = DateTimeUtils.getCurrentDateTimeInTimezone(TimeZoneEnum.AMERICA_NEW_YORK),

    @OneToMany(mappedBy = "employee", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val timeOffRequests: List<TimeOffRequest> = mutableListOf()
) {
    // Default constructor
    constructor() : this(UUID.randomUUID(), "", "", "", 0.0f)


    fun toDto(): EmployeeDto {
        return EmployeeDto(
            employeeId = employeeId,
            name = name,
            position = position,
            email = email,
            salary = salary,
            createdAt =
            DateTimeUtils.getDateTimeInTimezone(
                createdAt,
                TimeZoneEnum.AMERICA_NEW_YORK,
                TimeZoneEnum.ASIA_DUBAI
            ),
            modifiedAt =
            DateTimeUtils.getDateTimeInTimezone(
                modifiedAt,
                TimeZoneEnum.AMERICA_NEW_YORK,
                TimeZoneEnum.ASIA_DUBAI
            )

        )
    }

    companion object {
        fun fromCreateEmployeeDto(createEmployeeDto: CreateEmployeeDto): Employee {
            val now = DateTimeUtils.getCurrentDateTimeInTimezone(TimeZoneEnum.AMERICA_NEW_YORK)
            return Employee(
                name = createEmployeeDto.name,
                position = createEmployeeDto.position,
                email = createEmployeeDto.email,
                salary = createEmployeeDto.salary,
                createdAt = now,
                modifiedAt = now
            )
        }

        fun fromUpdateEmployeeDto(updateEmployeeDto: UpdateEmployeeDto): Employee {
            val now = DateTimeUtils.getCurrentDateTimeInTimezone(TimeZoneEnum.AMERICA_NEW_YORK)
            return Employee(
                name = updateEmployeeDto.name,
                position = updateEmployeeDto.position,
                email = updateEmployeeDto.email,
                salary = updateEmployeeDto.salary,
                modifiedAt = now
            )
        }
    }
}