package com.cercli.employeeservice.service

import com.cercli.employeeservice.dto.CreateTimeOffRequestDto
import com.cercli.employeeservice.entity.Employee
import com.cercli.employeeservice.entity.RequestCategory
import com.cercli.employeeservice.entity.TimeOffRequest
import com.cercli.employeeservice.enums.RequestCategoryName
import com.cercli.employeeservice.exception.ResourceNotFoundException
import com.cercli.employeeservice.repository.EmployeeRepository
import com.cercli.employeeservice.repository.RequestCategoryRepository
import com.cercli.employeeservice.repository.TimeOffRequestRepository
import com.cercli.employeeservice.util.DateTimeUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TimeOffRequestService(
    val employeeRepository: EmployeeRepository,
    val requestCategoryRepository: RequestCategoryRepository,
    private val timeOffRequestRepository: TimeOffRequestRepository
) {

    fun getAllTimeOffRequests(): List<TimeOffRequest> {
        return timeOffRequestRepository.findAll()
    }

    @Transactional
    fun createTimeOffRequestDto(createTimeOffRequestDto: CreateTimeOffRequestDto): TimeOffRequest {
        // Validate that the end date is after or equal to the start date
        DateTimeUtils.validateEndDateAfterOrEqualToStartDate(
            createTimeOffRequestDto.startDate,
            createTimeOffRequestDto.endDate
        )


        val employee = employeeRepository.findByEmployeeIdWithTimeOffRequests(createTimeOffRequestDto.employeeId)
            ?: throw ResourceNotFoundException("Employee not found with id: ${createTimeOffRequestDto.employeeId}")
        val requestCategory = requestCategoryRepository.findById(createTimeOffRequestDto.requestCategoryId)
            .orElseThrow { ResourceNotFoundException("Request category not found with id: ${createTimeOffRequestDto.requestCategoryId}") }

        // Check for overlapping requests with different categories
        hasOverlappingRequests(employee, createTimeOffRequestDto, requestCategory)
        if (hasOverlappingRequests(employee, createTimeOffRequestDto, requestCategory)) {
            throw IllegalArgumentException("Overlapping time-off request with a different category exists")
        }

        //save the request
        val timeOffRequest =
            TimeOffRequest.fromCreateTimeOffRequestDto(createTimeOffRequestDto, requestCategory, employee)
        return timeOffRequestRepository.save(timeOffRequest)
    }

    private fun hasOverlappingRequests(
        employee: Employee,
        createTimeOffRequestDto: CreateTimeOffRequestDto,
        requestCategory: RequestCategory
    ): Boolean {
        val newStartDate = createTimeOffRequestDto.startDate
        val newEndDate = createTimeOffRequestDto.endDate

        employee.timeOffRequests.forEach { timeOffRequest ->
            val requestedStartDate = timeOffRequest.startDate
            val requestedEndDate = timeOffRequest.endDate

            // Check for overlap
            if (newStartDate <= requestedEndDate && newEndDate >= requestedStartDate) {
                if (!isWorkRemotelyAndAnnualLeave(timeOffRequest.requestCategory.name, requestCategory.name)) {
                    return true
                }
            }
        }
        return false // No overlap found
    }

    private fun isWorkRemotelyAndAnnualLeave(existingCategoryName: String, newCategoryName: String): Boolean {
        val existingCategoryEnum = RequestCategoryName.fromCategoryName(existingCategoryName)
        val newCategoryEnum = RequestCategoryName.fromCategoryName(newCategoryName)

        return existingCategoryEnum == RequestCategoryName.WORK_REMOTELY && newCategoryEnum == RequestCategoryName.ANNUAL_LEAVE

    }

}
