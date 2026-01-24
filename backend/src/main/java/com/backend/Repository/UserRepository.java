package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
