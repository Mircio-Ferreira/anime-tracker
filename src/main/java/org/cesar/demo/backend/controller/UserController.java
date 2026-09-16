package org.cesar.demo.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.cesar.demo.backend.dto.user.UserRequest;
import org.cesar.demo.backend.dto.user.UserResponse;
import org.cesar.demo.backend.entity.User;
import org.cesar.demo.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "User management (creation and lookup)")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Find a user", description = "Returns the user for the given login, or 404 if it doesn't exist")
    @GetMapping("/{userLogin}")
    public ResponseEntity<UserResponse> findUser(@PathVariable String userLogin){
        User user = userService.findUser(userLogin);
        return  ResponseEntity.ok(UserResponse.fromEntity(user));
    }

    @Operation(summary = "List all users", description = "Returns every user currently stored")
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUser(){
        List<User> users = userService.findAllUser();
        List<UserResponse> userResponses = users.stream().map(UserResponse :: fromEntity).toList();
        return ResponseEntity.ok(userResponses);
    }

    @Operation(summary = "Create a user", description = "Creates a new user, or 409 if the login already exists")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request){
        User newUser = userService.createrUser(request.userName(),request.userLogin(), request.userPassword(), request.userRole());
        UserResponse userResponse = UserResponse.fromEntity(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
