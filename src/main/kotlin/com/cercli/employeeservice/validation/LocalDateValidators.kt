package com.cercli.employeeservice.validation

import java.time.LocalDate
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [LocalDateValidator::class])
annotation class ValidLocalDate(
    val message: String = "Invalid date format",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class LocalDateValidator : ConstraintValidator<ValidLocalDate, LocalDate> {

    override fun isValid(value: LocalDate?, context: ConstraintValidatorContext): Boolean {
        // If value is null, throw IllegalArgumentException
        if (value == null || value.toString().isBlank()) {
            throw IllegalArgumentException("Date cannot be blank")
        }

        try {
            // try parsing
            LocalDate.parse(value.toString())
            return true

        } catch (e: Exception) {
            // If an exception occurs during parsing, throw an error
            throw IllegalArgumentException("Invalid date format")
        }
    }
}
