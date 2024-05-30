package com.cercli.employeeservice.enums

import org.springframework.http.HttpStatus

data class ErrorCode(
    val code: String,
    val message: String,
    val httpStatus: HttpStatus,
) {
    companion object {
        val RESOURCE_NOT_FOUND = ErrorCode("001", "Not Found", HttpStatus.NOT_FOUND)
        val INVALID_TIMEOFF_REQUEST_EXCEPTION = ErrorCode("002", "Overlapping time-off request exist", HttpStatus.BAD_REQUEST)
        val INVALID_UUID = ErrorCode("003", "Invalid UUID", HttpStatus.BAD_REQUEST)

    }
}