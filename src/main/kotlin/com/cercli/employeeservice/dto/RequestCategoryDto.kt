package com.cercli.employeeservice.dto

import java.util.UUID

data class RequestCategoryDto(
    val requestCategoryId: UUID?,
    val name: String
)