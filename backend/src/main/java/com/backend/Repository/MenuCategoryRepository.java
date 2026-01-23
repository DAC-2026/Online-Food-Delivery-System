package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Entity.MenuCategory;
import com.backend.Entity.Restaurant;


public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
	public List<MenuCategory> findCategoriesByRestaurant(Restaurant restaurant);
}
