package com.api.parking.service;

import com.api.parking.common.ResponseUtil;
import com.api.parking.constant.MessageConstant;
import com.api.parking.form.MemberForm;
import com.api.parking.model.MemberModel;
import com.api.parking.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberSvcImpl implements MemberSvc {

    private final MemberRepository memberRepo;
    private final RedisSvc redisSvc;
    @Autowired
    public MemberSvcImpl(MemberRepository memberRepo,
                          RedisSvc redisSvc) {
        this.memberRepo = memberRepo;
        this.redisSvc = redisSvc;
    }

    @Override
    public ResponseEntity<Object> create(MemberForm form) {
        try {
            MemberModel member = new MemberModel();
            member.setName(form.getName());
            member.setKtm(form.getKtm());
            member.setGender(form.getGender());
            member.setAge(form.getAge());
            member.setPhone(form.getPhone());
            member.setJob(form.getJob());
            member.setAccessCode("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+Base64.getEncoder().encodeToString(form.getPhone().getBytes())+".png");
            MemberModel afterSave = memberRepo.save(member);
            redisSvc.putIfAbsen(afterSave);
            return ResponseUtil.build(MessageConstant.SUCCESS, member, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getAll() {
        try {
            List<MemberModel> members =  memberRepo.findAll();
            for (MemberModel member : members){
                Object data = redisSvc.getById(member.getId());
                if (data != null) continue;
                redisSvc.putIfAbsen(member);
            }
            HashMap<String, Object> dataSetRedis = (HashMap<String, Object>) redisSvc.hashGet();
            List<MemberModel> productList = new ArrayList<>();
            for (Object key : dataSetRedis.keySet()){
                MemberModel member = (MemberModel) redisSvc.getById(Long.valueOf(key.toString()));
                productList.add(member);
            }
            return ResponseUtil.build(MessageConstant.SUCCESS, productList, HttpStatus.OK);
        }catch (Exception e){
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<Object> findById(Long id) {
        try {
            Object data = redisSvc.getById(id);
            if (data == null) return ResponseUtil.build(MessageConstant.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
            return ResponseUtil.build(MessageConstant.SUCCESS, data, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updateById(MemberForm form, Long id) {
        try {
            Optional<MemberModel> getById = memberRepo.findById(id);
            if (!getById.isPresent()) return ResponseUtil.build(MessageConstant.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
            MemberModel member = getById.get();
            member.setName(form.getName());
            member.setKtm(form.getKtm());
            member.setGender(form.getGender());
            member.setAge(form.getAge());
            member.setPhone(form.getPhone());
            member.setJob(form.getJob());
            member.setAccessCode("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+ Base64.getEncoder().encodeToString(form.getPhone().getBytes())+".png");
            redisSvc.put(member.getId(), member);
            return ResponseUtil.build(MessageConstant.SUCCESS_UPDATE, memberRepo.save(member), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> deleteById(Long id) {
        try {
            Optional<MemberModel> data = memberRepo.findById(id);
            if (!data.isPresent()) return ResponseUtil.build(MessageConstant.DATA_NOT_FOUND, null, HttpStatus.NOT_FOUND);
            memberRepo.delete(data.get());
            redisSvc.delete(data.get().getId());
            return ResponseUtil.build(MessageConstant.SUCCESS_DELETE, null, HttpStatus.OK);
        }catch (Exception e){
            return ResponseUtil.build(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public MemberModel member(MemberForm form) {
        MemberModel member = new MemberModel();
            member.setName(form.getName());
            member.setKtm(form.getKtm());
            member.setGender(form.getGender());
            member.setAge(form.getAge());
            member.setPhone(form.getPhone());
            member.setJob(form.getJob());
            member.setAccessCode("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+Base64.getEncoder().encodeToString(form.getPhone().getBytes())+".png");
        return member;
    }
}
