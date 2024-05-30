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


    /**
     * Retrieves an employee by their ID.
     *
     * This method fetches the employee by employee ID. If the employee
     * with the specified ID does not exist, a ResourceNotFoundException is thrown.
     *
     * @param employeeId the ID of the employee to retrieve.
     * @return the employee entity corresponding to the provided ID.
     * @throws ResourceNotFoundException if no employee is found with the specified ID.
     */
    fun getEmployeeById(employeeId: UUID): Employee {
        val employee = employeeRepository.findById(employeeId)
            .orElseThrow { ResourceNotFoundException("Employee not found") }
        return employee
    }

    /**
     * Creates a new employee.
     *
     * This method creates a new employee entity based on the information provided in the
     * createEmployeeDto parameter.
     * @param createEmployeeDto the data transfer object containing the details of the new employee.
     * @return the newly created employee entity.
     */

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