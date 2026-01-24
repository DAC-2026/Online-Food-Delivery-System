package com.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Entity.MenuCategory;
import com.backend.Entity.MenuItem;


public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
	public List<MenuItem> findByCategory(MenuCategory category);
}
