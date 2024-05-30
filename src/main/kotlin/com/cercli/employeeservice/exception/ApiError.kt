package com.cercli.employeeservice.exception

import com.cercli.employeeservice.enums.ErrorCode
import org.springframework.http.HttpStatus

data class ApiError(val status: HttpStatus, val message: String, val errorCode: ErrorCode)

