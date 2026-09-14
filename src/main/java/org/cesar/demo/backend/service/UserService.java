package org.cesar.demo.backend.service;

import org.cesar.demo.backend.entity.AdminUser;
import org.cesar.demo.backend.entity.RegularUser;
import org.cesar.demo.backend.entity.User;
import org.cesar.demo.backend.enums.UserRole;
import org.cesar.demo.backend.exception.ConflictException;
import org.cesar.demo.backend.exception.NotFoundException;
import org.cesar.demo.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findUser(String userLogin){
        return userRepository.findByUserLogin((userLogin)).orElseThrow(
                () -> new NotFoundException("The login"+ userLogin + "is not found")
        );

    }

    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    public User createrUser(String userName, String userLogin, String userPassword, UserRole userRole){

        if(userRepository.findByUserLogin(userLogin).isPresent()){
            throw new ConflictException("The login"+ userLogin + "already exist");
        }

        User newUser = switch (userRole){
            case REGULAR -> new RegularUser(userName,userLogin,userPassword);
            case ADMIN -> new AdminUser(userName,userLogin,userPassword);
        };

        return userRepository.save(newUser);
    }

}
