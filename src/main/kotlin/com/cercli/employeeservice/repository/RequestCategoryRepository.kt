package com.cercli.employeeservice.repository

import com.cercli.employeeservice.entity.RequestCategory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID


interface RequestCategoryRepository : JpaRepository<RequestCategory, UUID>