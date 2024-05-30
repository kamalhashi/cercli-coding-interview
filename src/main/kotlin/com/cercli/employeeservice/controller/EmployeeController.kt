package com.cercli.employeeservice.controller

import com.cercli.employeeservice.dto.CreateEmployeeDto
import com.cercli.employeeservice.dto.EmployeeDto
import com.cercli.employeeservice.dto.UpdateEmployeeDto
import com.cercli.employeeservice.service.EmployeeService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/employees")
class EmployeeController(@Autowired private val employeeService: EmployeeService) {

    @Operation(summary = "Get Employee by ID", description = "Retrieve an employee by their unique ID.")
    @GetMapping("/{employeeId}")
    fun getEmployeeById(@PathVariable employeeId: UUID): ResponseEntity<EmployeeDto> {
        val employee = employeeService.getEmployeeById(employeeId)
        return ResponseEntity.ok(employee.toDto())
    }

    @Operation(summary = "Create Employee", description = "Create a new employee.")
    @PostMapping
    fun createEmployee(@RequestBody @Valid createEmployeeDto: CreateEmployeeDto): ResponseEntity<EmployeeDto> {
        val createdEmployee = employeeService.createEmployee(createEmployeeDto)
        return ResponseEntity(createdEmployee.toDto(), HttpStatus.CREATED)
    }

    @PutMapping("/{employeeId}")
    fun updateEmployee(@PathVariable employeeId: UUID, @RequestBody updateEmployeeDto: UpdateEmployeeDto): ResponseEntity<EmployeeDto> {
        val updatedEmployee = employeeService.updateEmployee(employeeId, updateEmployeeDto)
        return ResponseEntity.ok(updatedEmployee.toDto())
    }

    @GetMapping
    fun getAllEmployees(): ResponseEntity<List<EmployeeDto>> {
        val employees = employeeService.getAllEmployees()
        val employeeDtos = employees.map { it.toDto() }
        return ResponseEntity.ok(employeeDtos)
    }

}
