package kz.mandarin.shopping_center.controller;

import jakarta.validation.Valid;
import kz.mandarin.shopping_center.dto.UserRequest;
import kz.mandarin.shopping_center.dto.UserResponse;
import kz.mandarin.shopping_center.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        UserResponse userResponse = userService.findById(id);
        return ResponseEntity.ok(userResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
