package com.cercli.employeeservice

import com.cercli.employeeservice.dto.CreateEmployeeDto
import com.cercli.employeeservice.entity.Employee
import java.util.*

object TestUtils {
    fun mockExpectedEmployee(
        uuid: UUID,
        name: String = "Employee1",
        position: String = "Software Engineer",
        email: String = "employee1@example.com",
        salary: Float = 600f
    ): Employee {
        return Employee(
            employeeId = uuid,
            name = name,
            position = position,
            email = email,
            salary = salary
        )
    }

    fun mockCreateEmployeeDto(): CreateEmployeeDto {
        return CreateEmployeeDto(
            name = "Employee1",
            position = "Software Engineer",
            email = "employee1@example.com",
            salary = 600f
        )
    }
}
