package my.firstuserservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiControl {
    @GetMapping("/userapi")
    public String get() {
        return "Hi, User!";
    }
}
