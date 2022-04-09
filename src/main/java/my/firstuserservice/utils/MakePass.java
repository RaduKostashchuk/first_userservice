package my.firstuserservice.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MakePass {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("12345"));
    }
}
