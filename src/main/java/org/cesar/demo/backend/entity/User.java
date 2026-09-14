package org.cesar.demo.backend.entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Entity //Entity for jpa
@Inheritance(strategy = InheritanceType.JOINED) //Flag for hibernate to subhierarchy
@DiscriminatorColumn(name = "UserType") //Name for extra colum
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@Getter
@Setter

public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String userName;
    private String userLogin;
    private String userPassword;

    protected User(String userName,String userLogin,String userPassword){
        this.userName = userName;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
    }
}
