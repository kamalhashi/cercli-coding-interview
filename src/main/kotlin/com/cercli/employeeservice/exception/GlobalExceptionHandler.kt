package com.cercli.employeeservice.exception

import com.cercli.employeeservice.enums.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException, request: WebRequest?): ResponseEntity<Any> {
        log.error("handleResourceNotFoundException  : " + ex.message)
        val errorMessage = ex.message ?: "Resource not found"

        return getExceptionResponseEntity(HttpStatus.NOT_FOUND, errorMessage, ErrorCode.RESOURCE_NOT_FOUND)
    }

    @ExceptionHandler(OverlappingTimeOffRequestException::class)
    fun handleOverlappingTimeOffRequestException(
        ex: OverlappingTimeOffRequestException,
        request: WebRequest?
    ): ResponseEntity<Any> {
        val errorMessage = ex.message ?: "Overlapping time-off request with a different category exists"
        log.error("handleOverlappingTimeOffRequestException: $errorMessage")
        return getExceptionResponseEntity(
            HttpStatus.BAD_REQUEST,
            errorMessage,
            ErrorCode.INVALID_TIMEOFF_REQUEST_EXCEPTION
        )
    }

    @ExceptionHandler(InvalidUUIDException::class)
    fun handleInvalidUUIDException(
        ex: InvalidUUIDException,
        request: WebRequest?
    ): ResponseEntity<Any> {
        val errorMessage = ex.message ?: "Invalid UUID"
        log.error("handleInvalidUUIDException: $errorMessage")
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, errorMessage, ErrorCode.INVALID_UUID)
    }

    private fun getExceptionResponseEntity(
        httpStatus: HttpStatus,
        errorMessage: String,
        errorCode: ErrorCode
    ): ResponseEntity<Any> {
        val error = ApiError(httpStatus, errorMessage, errorCode)
        return ResponseEntity(error, httpStatus)
    }


}
