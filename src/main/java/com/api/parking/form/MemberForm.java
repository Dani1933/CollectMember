package com.api.parking.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MemberForm {
    private String name;
    private String ktm;
    private String gender;
    private Integer age;
    private String phone;
    private String job;
    private String noMember;

}
