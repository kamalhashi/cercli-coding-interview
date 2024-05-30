package com.cercli.employeeservice.repository

import com.cercli.employeeservice.enums.Location
import com.cercli.employeeservice.entity.PublicHoliday
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface PublicHolidayRepository : JpaRepository<PublicHoliday, Long> {
    fun findByLocationInAndDateBetween(locations: List<Location>, startDate: LocalDate, endDate: LocalDate): List<PublicHoliday>
    fun findByLocation(location: Location) : List<PublicHoliday>
}
