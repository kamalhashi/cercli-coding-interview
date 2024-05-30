package com.cercli.employeeservice.entity

import com.cercli.employeeservice.dto.CreateTimeOffRequestDto
import com.cercli.employeeservice.dto.TimeOffRequestDto
import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "time_off_request")
data class TimeOffRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val timeOffRequestId: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "request_category_id", nullable = false)
    val requestCategory: RequestCategory,

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    val employee: Employee,

    @Column(name = "start_date", nullable = false)
    val startDate: LocalDate,

    @Column(name = "end_date", nullable = false)
    val endDate: LocalDate
) {
    constructor() : this(UUID.randomUUID(), RequestCategory(), Employee(), LocalDate.now(), LocalDate.now())

    fun toDto(): TimeOffRequestDto {
        return TimeOffRequestDto(
            timeOffRequestId = this.timeOffRequestId,
            requestCategory = this.requestCategory.toDto(),
            employee = this.employee.toDto(),
            startDate = this.startDate,
            endDate = this.endDate
        )
    }


    companion object {
        fun fromCreateTimeOffRequestDto(
            createTimeOffRequestDto: CreateTimeOffRequestDto,
            requestCategory: RequestCategory,
            employee: Employee
        ): TimeOffRequest {
            return TimeOffRequest(
                requestCategory = requestCategory,
                employee = employee,
                startDate = createTimeOffRequestDto.startDate,
                endDate = createTimeOffRequestDto.endDate
            )
        }
    }

}