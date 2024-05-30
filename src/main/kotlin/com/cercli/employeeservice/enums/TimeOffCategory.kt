package com.cercli.employeeservice.enums

enum class RequestCategoryName(val categoryName: String) {
    WORK_REMOTELY("Work Remotely"),
    ANNUAL_LEAVE("Annual Leave"),
    SICK_LEAVE("Sick Leave"),
    PERSONAL_LEAVE("Personal Leave");

    override fun toString(): String {
        return categoryName
    }

    companion object {
        fun fromCategoryName(categoryName: String): RequestCategoryName {
            return entries.find { it.categoryName == categoryName }
                ?: throw IllegalArgumentException("Invalid category name provided: $categoryName")
        }
    }
}