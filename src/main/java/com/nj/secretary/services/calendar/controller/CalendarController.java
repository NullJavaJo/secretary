package com.nj.secretary.services.calendar.controller;

import com.nj.secretary.services.calendar.domain.Calendar;
import com.nj.secretary.services.calendar.service.CalendarService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calendar/*")
public class CalendarController {

    @Autowired
    @Qualifier("calendarServiceImpl")
    CalendarService calendarService;

    @GetMapping("mainCalendar")
    public String mainCalendarView(){

        return "calendar/index";
    }

}
