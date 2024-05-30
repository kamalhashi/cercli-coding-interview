package com.cercli.employeeservice.controller


import com.cercli.employeeservice.dto.CreateTimeOffRequestDto
import com.cercli.employeeservice.dto.TimeOffRequestDto
import com.cercli.employeeservice.service.TimeOffRequestService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/timeOfRequests")
class TimeOffRequestController(
    private val timeOffRequestService: TimeOffRequestService
) {
    @PostMapping
    fun createTimeOffRequest(@RequestBody @Valid createTimeOffRequestDto: CreateTimeOffRequestDto): ResponseEntity<TimeOffRequestDto> {
        val timeOffRequest = timeOffRequestService.createTimeOffRequestDto(createTimeOffRequestDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(timeOffRequest.toDto())
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<TimeOffRequestDto>> {
        val timeOffRequests = timeOffRequestService.getAllTimeOffRequests()
        val timeOffRequestDtos = timeOffRequests.map { it.toDto() }
        return ResponseEntity.ok(timeOffRequestDtos)

    }
}