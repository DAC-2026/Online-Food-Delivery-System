package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.Entity.Address;
import com.backend.Entity.User;

public interface AddressRepository extends JpaRepository<Address, Long> {
	List<Address> findAllByUser(User user);
}
