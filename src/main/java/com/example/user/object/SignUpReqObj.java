package com.example.user.object;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpReqObj {
    private String username;
    private String password;
    private String email;
    private String name;
    private String role;
}
