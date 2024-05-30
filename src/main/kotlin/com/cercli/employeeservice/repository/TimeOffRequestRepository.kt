package com.cercli.employeeservice.repository

import com.cercli.employeeservice.entity.TimeOffRequest
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TimeOffRequestRepository : JpaRepository<TimeOffRequest, UUID>