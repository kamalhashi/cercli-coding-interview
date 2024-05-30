package com.cercli.employeeservice.controller

import com.cercli.employeeservice.dto.RequestCategoryDto
import com.cercli.employeeservice.service.RequestCategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/requestCategories")
class RequestCategoryController(private val requestCategoryService: RequestCategoryService) {

    @GetMapping
    fun getAllRequestCategories(): ResponseEntity<List<RequestCategoryDto>> {
        val requestCategories = requestCategoryService.getAllRequestCategories()
        val requestCategoryDtos = requestCategories.map { it.toDto() }
        return ResponseEntity.ok(requestCategoryDtos)
    }
}