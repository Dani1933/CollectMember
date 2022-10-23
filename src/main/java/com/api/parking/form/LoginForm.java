package com.api.parking.form;

import lombok.Data;

@Data
public class LoginForm {
    private String username;
    private String password;
    private boolean active;
    private String role;
}
