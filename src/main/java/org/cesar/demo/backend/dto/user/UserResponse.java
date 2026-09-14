package org.cesar.demo.backend.dto.user;

import org.cesar.demo.backend.entity.User;

public record UserResponse(Long id, String userName, String userLogin) {

    public static UserResponse fromEntity(User user){
        return new UserResponse(user.getId(),user.getUserName(),user.getUserLogin());
    }
}
