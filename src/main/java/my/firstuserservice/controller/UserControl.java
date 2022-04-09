package my.firstuserservice.controller;

import my.firstuserservice.dto.UserDto;
import my.firstuserservice.model.User;
import my.firstuserservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserControl {
    private final UserService userService;

    public UserControl(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<UserDto> save(@Valid @RequestBody User user) {
        return new ResponseEntity<>(UserDto.of(userService.save(user)), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable int id) {
        return new ResponseEntity<>(UserDto.of(userService.findById(id)), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<UserDto>> findAll() {
        List<UserDto> result = new ArrayList<>();
        userService.findAll().forEach(e -> result.add(UserDto.of(e)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/")
    public ResponseEntity<Void> update(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok().build();
    }
}
