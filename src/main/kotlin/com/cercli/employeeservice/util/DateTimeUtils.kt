package com.cercli.employeeservice.util

import com.cercli.employeeservice.enums.TimeZoneEnum
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

object DateTimeUtils {
    fun getCurrentDateTimeInTimezone(timezone: TimeZoneEnum): LocalDateTime {
        val zoneId = ZoneId.of(timezone.zoneId)
        return ZonedDateTime.now(zoneId).toLocalDateTime()
    }

    fun getDateTimeInTimezone(localDateTime: LocalDateTime, sourceTimeZoneEnum: TimeZoneEnum, targetTimeZoneEnum: TimeZoneEnum): LocalDateTime {
        val sourceZoneId = ZoneId.of(sourceTimeZoneEnum.zoneId)
        val targetZoneId = ZoneId.of(targetTimeZoneEnum.zoneId)

        val sourceZonedDateTime = localDateTime.atZone(sourceZoneId)
        val targetZonedDateTime = sourceZonedDateTime.withZoneSameInstant(targetZoneId)
        return targetZonedDateTime.toLocalDateTime()

    }

    fun validateEndDateAfterOrEqualToStartDate(startDate: LocalDate, endDate: LocalDate) {
        if (endDate < startDate) {
            throw IllegalArgumentException("End date must be after or equal to start date")
        }
    }
}