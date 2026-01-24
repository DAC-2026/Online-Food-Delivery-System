package com.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.Entity.MenuItemRating;

public interface MenuItemRatingRepository extends JpaRepository<MenuItemRating, Long> {

	Optional<MenuItemRating> findByMenuItemIdAndUserId(Long menuItemId, Long userId);
}
