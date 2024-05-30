package com.cercli.employeeservice.dto


import com.cercli.employeeservice.validation.ValidLocalDate
import com.cercli.employeeservice.validation.ValidUUID
import java.time.LocalDate
import java.util.UUID

data class TimeOffRequestDto(
    val timeOffRequestId: UUID?,
    val requestCategory: RequestCategoryDto,
    val employee: EmployeeDto,
    val startDate: LocalDate,
    val endDate: LocalDate
)

data class CreateTimeOffRequestDto(
    @ValidUUID
    val employeeId: UUID,
    @ValidUUID
    val requestCategoryId: UUID,
    @ValidLocalDate
    val startDate: LocalDate,
    @ValidLocalDate
    val endDate: LocalDate
)