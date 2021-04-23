package com.controller;

import com.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/get")
    public ResponseEntity getNotifications(@RequestParam(value = "userid", defaultValue = "") String userid) {
        return notificationService.getNotifications(userid);
    }

    @GetMapping("/read")
    public ResponseEntity removeNotification(@RequestParam(value = "notificationid", defaultValue = "") String notificationid) {
        return notificationService.removeNotifications(notificationid);
    }
}
