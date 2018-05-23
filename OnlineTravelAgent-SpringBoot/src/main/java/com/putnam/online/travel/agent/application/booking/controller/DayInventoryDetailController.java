package com.putnam.online.travel.agent.application.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.putnam.online.travel.agent.application.booking.model.entity.DailyInventoryDetail;
import com.putnam.online.travel.agent.application.booking.service.DayInventoryDetailService;

@RestController
@RequestMapping("dayinventorydetail")
public class DayInventoryDetailController {

    @Autowired
    private DayInventoryDetailService dayInventoryDetailService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Long addDayInventoryDetail(@RequestBody DailyInventoryDetail dayInventoryDetail) {
	return dayInventoryDetailService.addDayInventoryDetail(dayInventoryDetail);
    }
}
