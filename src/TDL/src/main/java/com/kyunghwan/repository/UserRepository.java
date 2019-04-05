package com.kyunghwan.repository;

import com.kyunghwan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(String id);

    User findByEmail(String email);
}