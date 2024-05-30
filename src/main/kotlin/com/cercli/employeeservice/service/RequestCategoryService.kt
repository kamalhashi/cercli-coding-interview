package com.cercli.employeeservice.service

import com.cercli.employeeservice.entity.RequestCategory
import com.cercli.employeeservice.repository.RequestCategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RequestCategoryService(private val requestCategoryRepository: RequestCategoryRepository) {

    @Transactional
    fun saveAll(requestCategories: List<RequestCategory>): List<RequestCategory> {
        return requestCategoryRepository.saveAll(requestCategories)
    }

    fun getAllRequestCategories(): List<RequestCategory> {
        return requestCategoryRepository.findAll()
    }
}