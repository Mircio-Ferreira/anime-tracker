package org.cesar.demo.backend.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("RegularUser")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegularUser extends User{

    public RegularUser(String userName, String userLogin, String userPassword){
        super(userName,userLogin,userPassword);
    }

}
