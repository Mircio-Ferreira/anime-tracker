package org.cesar.demo.backend.dto.user;

import org.cesar.demo.backend.enums.UserRole;

public record UserRequest(String userName, String userLogin, String userPassword, UserRole userRole) {

}
