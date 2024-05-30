package com.cercli.employeeservice.service

import com.cercli.employeeservice.TestUtils
import com.cercli.employeeservice.dto.CreateTimeOffRequestDto
import com.cercli.employeeservice.entity.RequestCategory
import com.cercli.employeeservice.entity.TimeOffRequest
import com.cercli.employeeservice.enums.RequestCategoryName
import com.cercli.employeeservice.exception.ResourceNotFoundException
import com.cercli.employeeservice.repository.EmployeeRepository
import com.cercli.employeeservice.repository.RequestCategoryRepository
import com.cercli.employeeservice.repository.TimeOffRequestRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDate
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = ["/test-data-script.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TimeOffRequestServiceTest {

    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    @Autowired
    private lateinit var requestCategoryRepository: RequestCategoryRepository

    @Autowired
    private lateinit var timeOffRequestRepository: TimeOffRequestRepository

    @Test
    fun `test createTimeOffRequestDto should create and return TimeOffRequest`() {
        val employeeId =  UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479")
        val requestCategoryId = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479")
        val startDate = LocalDate.now()
        val endDate = startDate.plusDays(2)
        val createTimeOffRequestDto = CreateTimeOffRequestDto(employeeId, requestCategoryId, startDate, endDate)

        val employee = employeeRepository.findById(employeeId).get()
        val requestCategory = RequestCategory(requestCategoryId, RequestCategoryName.ANNUAL_LEAVE.categoryName)


        val timeOffRequestService = TimeOffRequestService(employeeRepository, requestCategoryRepository, timeOffRequestRepository)
        val result = timeOffRequestService.createTimeOffRequestDto(createTimeOffRequestDto)

        assertEquals(employee.employeeId, result.employee.employeeId)
        assertEquals(requestCategory.requestCategoryId, result.requestCategory.requestCategoryId)
        assertEquals(startDate, result.startDate)
        assertEquals(endDate, result.endDate)
    }

    @Test
    fun `test createTimeOffRequestDto should throw ResourceNotFoundException for non-existent employee`() {
        val employeeId = UUID.fromString("be5b0c38-df80-40c2-b3ae-de6443ee6844")
        val requestCategoryId = UUID.fromString("32cbdb81-307a-493c-85f0-d207e1ed565e")
        val startDate = LocalDate.now()
        val endDate = startDate.plusDays(2)
        val createTimeOffRequestDto = CreateTimeOffRequestDto(employeeId, requestCategoryId, startDate, endDate)

        val timeOffRequest = TimeOffRequestService(employeeRepository, requestCategoryRepository, timeOffRequestRepository)

        assertThrows(ResourceNotFoundException::class.java) {
            timeOffRequest.createTimeOffRequestDto(createTimeOffRequestDto)
        }
    }
}
