package com.cercli.employeeservice.controller

import com.cercli.employeeservice.TestUtils
import com.cercli.employeeservice.exception.ResourceNotFoundException
import com.cercli.employeeservice.service.EmployeeService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*


@WebMvcTest(EmployeeControllerTest::class)
@ExtendWith(MockitoExtension::class)
@ExtendWith(MockitoExtension::class)
class EmployeeControllerTest {

    @MockBean
    private lateinit var employeeService: EmployeeService

    @Autowired
    private lateinit var mockMvc: MockMvc
    private val objectMapper: ObjectMapper = jacksonObjectMapper()


    @Test
    fun `test getEmployeeById with valid employeeId, should result  200 status`() {
        val employeeId = UUID.randomUUID()
        val expectedEmployee = TestUtils.mockExpectedEmployee(employeeId)
        `when`(employeeService.getEmployeeById(employeeId)).thenReturn(expectedEmployee)

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{employeeId}", employeeId))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(expectedEmployee.employeeId.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedEmployee.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.position").value(expectedEmployee.position))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(expectedEmployee.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(expectedEmployee.salary.toString()))
    }

    @Test
    fun `test getEmployeeById with invalid Id, should result 404 status`() {
        val employeeId = UUID.randomUUID()
        `when`(employeeService.getEmployeeById(employeeId)).thenThrow(ResourceNotFoundException("Employee not found"))
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{employeeId}", employeeId))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Employee not found"))

    }

    @Test
    fun `test createEmployee with an invalid request body should result 404 status`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `test createEmployee should return 201 Created status`() {
        val createEmployeeDto = TestUtils.mockCreateEmployeeDto()
        val expectedEmployee = TestUtils.mockExpectedEmployee(UUID.randomUUID())

        `when`(employeeService.createEmployee(any()))
            .thenReturn(expectedEmployee)

        val json = objectMapper.writeValueAsString(createEmployeeDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(expectedEmployee.employeeId.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedEmployee.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.position").value(expectedEmployee.position))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(expectedEmployee.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(expectedEmployee.salary.toString()))
    }


    @Test
    fun `should return all employees when getAllEmployees is called`() {
        val employee1 = TestUtils.mockExpectedEmployee(UUID.randomUUID())
        val employee2 = TestUtils.mockExpectedEmployee(UUID.randomUUID())
        val expectedEmployees = listOf(employee1, employee2)

        `when`(employeeService.getAllEmployees()).thenReturn(expectedEmployees)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/employees")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeId").value(employee1.employeeId.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employee1.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].position").value(employee1.position))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(employee1.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(employee1.salary.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].employeeId").value(employee2.employeeId.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(employee2.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].position").value(employee2.position))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value(employee2.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].salary").value(employee2.salary.toString()))
    }

    @Test
    fun `should return empty list when no employees are found`() {
        `when`(employeeService.getAllEmployees()).thenReturn(emptyList())

        mockMvc.perform(
            MockMvcRequestBuilders.get("/employees")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty)
    }

}
