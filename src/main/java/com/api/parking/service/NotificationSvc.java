package com.api.parking.service;

import com.api.parking.form.NotificationForm;
import org.springframework.http.ResponseEntity;

public interface NotificationSvc {
    ResponseEntity<Object> getNotif(NotificationForm form);
}
