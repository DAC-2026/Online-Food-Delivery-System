package com.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backend.Entity.CustomerOrder;
import com.backend.Entity.User;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
	List<CustomerOrder> findAllByUser(User user);
    
    Optional<CustomerOrder> findByRazorpayOrderId(String razorpayOrderId);
}
