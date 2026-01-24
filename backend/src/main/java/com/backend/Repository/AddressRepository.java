package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.Entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
