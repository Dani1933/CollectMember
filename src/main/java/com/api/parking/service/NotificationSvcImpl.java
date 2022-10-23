package com.api.parking.service;

import com.api.parking.common.ResponseUtil;
import com.api.parking.constant.MessageConstant;
import com.api.parking.form.NotificationForm;
import com.api.parking.model.Notification;
import com.api.parking.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationSvcImpl implements NotificationSvc{

    private final NotificationRepository repository;

    @Autowired
    public NotificationSvcImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<Object> getNotif(NotificationForm form) {
        try {
            List<Notification> notifications = repository.findAll();
            if (form != null &&  form.getFilter() != null && form.getFilter().equals(NotificationForm.READ_)){
                notifications = notifications.stream()
                        .filter(Notification::is_read)
                        .collect(Collectors.toList());
            } else if (form != null &&  form.getFilter() != null && form.getFilter().equals(NotificationForm.UNREAD_)){
                notifications = notifications.stream()
                        .filter(v->!v.is_read())
                        .collect(Collectors.toList());
            }

            return ResponseUtil.build(MessageConstant.SUCCESS, notifications, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.build(MessageConstant.INTERNAL_SERVER_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
