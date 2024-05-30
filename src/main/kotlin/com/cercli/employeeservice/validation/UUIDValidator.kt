package com.cercli.employeeservice.validation

import com.cercli.employeeservice.exception.InvalidUUIDException
import kotlin.reflect.KClass
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import java.util.*

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UUIDValidator::class])
annotation class ValidUUID(
    val message: String = "{invalid.uuid}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class UUIDValidator : ConstraintValidator<ValidUUID, UUID> {

    override fun isValid(value: UUID?, context: ConstraintValidatorContext): Boolean {
        if (value == null || !isValidUUID(value.toString())) {
            throw InvalidUUIDException("Invalid UUID")
        }
        return true
    }

    private fun isValidUUID(uuid: String): Boolean {
        val uuidRegex = Regex("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}")
        return uuid.matches(uuidRegex)
    }
}