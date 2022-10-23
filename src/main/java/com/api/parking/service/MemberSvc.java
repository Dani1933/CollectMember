package com.api.parking.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.api.parking.form.MemberForm;
import com.api.parking.model.MemberModel;

import java.util.List;

public interface MemberSvc {
//    MemberModel create(MemberForm form);
    ResponseEntity<Object>create(MemberForm form);
//    List<MemberModel> getAll();
    ResponseEntity<Object> getAll();
    ResponseEntity<Object> findById(Long id);

    ResponseEntity<Object> updateById(MemberForm form, Long id);

    ResponseEntity<Object> deleteById(Long id);

}
