package com.cercli.employeeservice.service

import com.cercli.employeeservice.dto.CreateEmployeeDto
import com.cercli.employeeservice.dto.UpdateEmployeeDto
import com.cercli.employeeservice.entity.Employee
import com.cercli.employeeservice.exception.ResourceNotFoundException
import com.cercli.employeeservice.repository.EmployeeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class EmployeeService(val employeeRepository: EmployeeRepository) {

    fun getEmployeeById(employeeId: UUID): Employee {
        val employee = employeeRepository.findById(employeeId)
            .orElseThrow { ResourceNotFoundException("Employee not found") }
        return employee
    }

    @Transactional
    fun createEmployee(createEmployeeDto: CreateEmployeeDto): Employee {
        val employee = Employee.fromCreateEmployeeDto(createEmployeeDto)
        return employeeRepository.save(employee)
    }

    @Transactional
    fun updateEmployee(employeeId: UUID, updateEmployeeDto: UpdateEmployeeDto): Employee {
        employeeRepository.findById(employeeId)
            .orElseThrow { ResourceNotFoundException("Employee not found") }

        val updatedEmployee = Employee.fromUpdateEmployeeDto(updateEmployeeDto)
        return employeeRepository.save(updatedEmployee)
    }

    @Transactional
    fun saveAll(employees: List<Employee>): List<Employee> {
        return employeeRepository.saveAll(employees)
    }

    fun getAllEmployees(): List<Employee> {
        return employeeRepository.findAll()
    }
}