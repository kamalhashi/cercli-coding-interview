package com.cercli.employeeservice.initialization

import com.cercli.employeeservice.entity.Employee
import com.cercli.employeeservice.service.EmployeeService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class EmployeeInitializer(private val employeeService: EmployeeService) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        // Initialize Employee entities
        val mockedEmployees = mutableListOf<Employee>()

        // Employee 1
        mockedEmployees.add(
            Employee(
                name = "Employee 1",
                position = "Software Engineer",
                email = "employee1@example.com",
                salary = 50000.0f
            )
        )

        // Employee 2
        mockedEmployees.add(
            Employee(
                name = "Employee 2",
                position = "Software Engineer",
                email = "employee2@example.com",
                salary = 60000.0f
            )
        )

        // Employee 3
        mockedEmployees.add(
            Employee(
                name = "Employee 3",
                position = "Software Engineer",
                email = "employee3@example.com",
                salary = 60000.0f
            )
        )
        employeeService.saveAll(mockedEmployees)
    }
}