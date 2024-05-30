package com.cercli.employeeservice.service

import com.cercli.employeeservice.dto.CreateTimeOffRequestDto
import com.cercli.employeeservice.entity.Employee
import com.cercli.employeeservice.entity.RequestCategory
import com.cercli.employeeservice.entity.TimeOffRequest
import com.cercli.employeeservice.enums.RequestCategoryName
import com.cercli.employeeservice.exception.OverlappingTimeOffRequestException
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

    /**
     * Creates a new time off request for an employee.
     *
     * This method validates that the end date is after or equal to the start date, retrieves the employee and request category,
     * checks for overlapping requests with different categories, and then saves the time off request.
     *
     * @param createTimeOffRequestDto the data transfer object containing the details of the time off request to be created.
     * @return the saved TimeOffRequest entity.
     * @throws ResourceNotFoundException if the employee or request category is not found.
     * @throws OverlappingTimeOffRequestException if there is an overlapping time-off request with a different category.
     */
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
            throw OverlappingTimeOffRequestException("Overlapping time-off request with a different category exists")
        }

        //save the request
        val timeOffRequest =
            TimeOffRequest.fromCreateTimeOffRequestDto(createTimeOffRequestDto, requestCategory, employee)
        return timeOffRequestRepository.save(timeOffRequest)
    }


    /**
     * Checks if there are overlapping time-off requests for the given employee.
     *
     * This method iterates through the employee's existing time-off requests and checks if there is an overlap
     * with the new time-off request dates. If an overlap is found then we check whether existing time-off-request is "Work Remote"
     *
     * @param employee the employee whose time-off requests are being checked.
     * @param createTimeOffRequestDto the data transfer object containing the details of the new time off request.
     * @param requestCategory the request category of the new time off request.
     * @return true if there is an overlapping time-off request with a different category, false otherwise.
     */
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

    /**
     * Employee can request "Annual Leave " while  "Working Remotely"
     *
     * This method compares the existing and new request category names to determine if one is "Work Remotely" and
     * the other is "Annual Leave".
     *
     * @param existingCategoryName the name of the existing request category.
     * @param newCategoryName the name of the new request category.
     * @return true if the existing category is "Work Remotely" and the new category is "Annual Leave", false otherwise.
     */
    private fun isWorkRemotelyAndAnnualLeave(existingCategoryName: String, newCategoryName: String): Boolean {
        val existingCategoryEnum = RequestCategoryName.fromCategoryName(existingCategoryName)
        val newCategoryEnum = RequestCategoryName.fromCategoryName(newCategoryName)

        return existingCategoryEnum == RequestCategoryName.WORK_REMOTELY && newCategoryEnum == RequestCategoryName.ANNUAL_LEAVE

    }

}
