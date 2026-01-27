package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.Entity.MenuCategory;
import com.backend.Entity.MenuItem;


public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
	public List<MenuItem> findByCategory(MenuCategory category);
	@Query("""
	        SELECT m
	        FROM MenuItem m
	        JOIN m.category c
	        JOIN c.restaurant r
	        WHERE r.id = :restaurantId
	    """)
	    List<MenuItem> findMenuItemsByRestaurantId(Long restaurantId);
	
}
