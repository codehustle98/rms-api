package com.codehustle.rms.repository;

import com.codehustle.rms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserEmailId(String emailId);

    User findByUserId(Long userId);
}
