package org.cesar.demo.backend.controller;

import jakarta.validation.Valid;
import org.cesar.demo.backend.dto.user.UserRequest;
import org.cesar.demo.backend.dto.user.UserResponse;
import org.cesar.demo.backend.entity.User;
import org.cesar.demo.backend.exception.ConflictException;
import org.cesar.demo.backend.exception.NotFoundException;
import org.cesar.demo.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{userLogin}")
    public ResponseEntity<UserResponse> findUser(@PathVariable String userLogin){
        try{
            User user = userService.findUser(userLogin);
            return  ResponseEntity.ok(UserResponse.fromEntity(user));
        } catch (NotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUser(){
        List<User> users = userService.findAllUser();
        List<UserResponse> userResponses = users.stream().map(UserResponse :: fromEntity).toList();
        return ResponseEntity.ok(userResponses);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request){
        try{
            User newUser = userService.createrUser(request.userName(),request.userLogin(), request.userPassword(), request.userRole());
            UserResponse userResponse = UserResponse.fromEntity(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        }catch (ConflictException exception){
            throw new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage());
        }
    }
}
