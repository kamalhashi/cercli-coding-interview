package com.cercli.employeeservice.initialization

import com.cercli.employeeservice.entity.RequestCategory
import com.cercli.employeeservice.service.RequestCategoryService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class RequestCategoryInitializer(private val requestCategoryService: RequestCategoryService) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        // Initialize RequestCategory entities
        val mockedRequestCategories = mutableListOf<RequestCategory>(
            RequestCategory(name = "Annual Leave"),
            RequestCategory(name = "Sick Leave"),
            RequestCategory(name = "Work Remotely")
        )
        requestCategoryService.saveAll(mockedRequestCategories)
    }
}