package com.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

}
