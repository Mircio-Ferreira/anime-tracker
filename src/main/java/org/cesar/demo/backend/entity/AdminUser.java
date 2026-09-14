package org.cesar.demo.backend.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminUser extends User {

    public AdminUser(String userName,String userLogin,String userPassword){
        super(userName,userLogin,userPassword);
    }

}
