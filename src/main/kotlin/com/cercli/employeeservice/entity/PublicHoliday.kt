package com.cercli.employeeservice.entity


import com.cercli.employeeservice.enums.Location
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate



@Entity
data class PublicHoliday(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val date: LocalDate,
    val name: String,
    @Enumerated(EnumType.STRING)
    val location: Location
) {
    // This constructor is called by Hibernate when it needs to instantiate the entity.
    constructor() : this(0, LocalDate.now(), "", Location.US)
}