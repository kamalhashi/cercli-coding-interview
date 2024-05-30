package com.cercli.employeeservice.repository

import com.cercli.employeeservice.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID


interface EmployeeRepository : JpaRepository<Employee, UUID> {
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.timeOffRequests WHERE e.employeeId = :employeeId")
    fun findByEmployeeIdWithTimeOffRequests(employeeId: UUID): Employee?
}