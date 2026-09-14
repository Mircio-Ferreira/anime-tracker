package org.cesar.demo.backend.repository;

import org.cesar.demo.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User,Long> {

    Optional<User> findByUserLogin(String userLogin);

}
