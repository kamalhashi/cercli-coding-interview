package com.cercli.employeeservice.service

import com.cercli.employeeservice.exception.ResourceNotFoundException
import com.cercli.employeeservice.repository.EmployeeRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Sql(scripts = ["/test-data-script.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EmployeeServiceIntegrationTest {

    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    @Autowired
    private lateinit var employeeService: EmployeeService

    @Test
    fun `test getEmployeeById should return employee when employee exists`() {
        val employeeId = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479")
        val expectedEmployee =  employeeRepository.findById(employeeId).get()

        val result = employeeService.getEmployeeById(employeeId)

        assertEquals(expectedEmployee, result)
    }

    @Test
    fun `test getEmployeeById should throw ResourceNotFoundException when employee does not exist`() {
        val employeeId = UUID.randomUUID()
        val employeeService = EmployeeService(employeeRepository)

        assertThrows(ResourceNotFoundException::class.java) {
            employeeService.getEmployeeById(employeeId)
        }
    }


}
