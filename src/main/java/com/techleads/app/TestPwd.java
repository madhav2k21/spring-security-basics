package com.techleads.app;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestPwd {

    public static void main(String[] args) {
        String pwd=
        passwordEncoder().encode("madhav1");
        System.out.println(pwd);

    }

    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B);
    }
}
