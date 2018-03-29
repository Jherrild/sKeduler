package com.jherrild.web

import org.w3c.dom.events.Event
import org.w3c.xhr.XMLHttpRequest

/**
 * @author jherrild@expedia.com
 * Created on 3/25/18
 */
class Main {
    val BASE_URL = "http://localhost:8080/skeduler"
    val CALENDAR_OPERATIONS = "/calendars"

    fun main(args: Array<String>) {

    }

    fun getAllCalendars(): Unit {
        val requestString = BASE_URL + CALENDAR_OPERATIONS
        val request = XMLHttpRequest()

        request.onloadend = fun(event: Event) {

        }
    }

    fun getEventsByCalendarAndDate(calendarName : String, date : Long) {
        val requestString = "$BASE_URL$CALENDAR_OPERATIONS/$calendarName/$date/events"
        val request = XMLHttpRequest()


    }
}