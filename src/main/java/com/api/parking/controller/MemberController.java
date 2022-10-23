package com.api.parking.controller;

import com.api.parking.form.MemberForm;
import com.api.parking.model.MemberModel;
import com.api.parking.service.MemberSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberSvc memberSvc;

    @Autowired
    public  MemberController(MemberSvc memberSvc){
        this.memberSvc = memberSvc;
    }
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody MemberForm form){
        return memberSvc.create(form);
    }
    @GetMapping
    public ResponseEntity<Object> getAll(){
        return memberSvc.getAll();
    }

    @GetMapping(value = "{id}")
    //    query params
    //    public ParkingModel getById(@RequestParam Long id){
    //        return parkingSvc.findById(id);
    //    }

    public ResponseEntity<Object> getById(@PathVariable  Long id){
        return memberSvc.findById(id);
    }

    @PostMapping(value = "{id}")
    public ResponseEntity<Object> update(@RequestBody MemberForm form, @PathVariable Long id){
        return memberSvc.updateById(form, id);
    }


    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id){
        return memberSvc.deleteById(id);
    }
}
