# 🎓 Interview Preparation Guide: Online Food Ordering System

This guide covers potential interview questions based on the specific architecture and logic of your Restaurant Management System.

## 🏗️ Architecture & Spring Boot

**Q: Can you explain the architecture of your application?**
**A:** The application follows a classic layered architecture using Spring Boot:
1.  **Controller Layer** (`com.backend.controller`): Handles HTTP requests, validates input using DTOs, and returns HTTP responses.
2.  **Service Layer** (`com.backend.service`): Contains business logic (e.g., placing orders, calculating ratings). It uses `@Transactional` to ensure data integrity.
3.  **Repository Layer** (`com.backend.Repository`): interact with the database using Spring Data JPA.
4.  **Entity Layer** (`com.backend.Entity`): Represents the database schema using JPA annotations.

**Q: Why do you use DTOs (Data Transfer Objects) instead of returning Entities directly?**
**A:** 
- **Decoupling:** Decouples the internal database schema from the external API contract.
- **Security:** Prevents exposing sensitive data (like `password` or internal IDs) inadvertently.
- **Infinite Recursion:** Prevents circular reference issues in JSON serialization (e.g., Restaurant -> MenuItems -> Restaurant).
- We use `ModelMapper` to convert between Entities and DTOs efficiently.

---

## 💾 Database & JPA

**Q: How are relationships handled in your database?**
**A:** We use JPA annotations to define relationships:
- **One-to-Many**: `Restaurant` has many `MenuCategory`, `MenuCategory` has many `MenuItem`.
- **Many-to-One**: `MenuItem` belongs to a `MenuCategory`. `MenuItemRating` belongs to a `User`, `Restaurant`, and `MenuItem`.
- **Cascade Types**: We use `CascadeType.ALL` and `orphanRemoval=true` for composition relationships, such as `MenuItem` -> `MenuItemRating`. This ensures that if a menu item is deleted, all its ratings are also deleted automatically (fixing foreign key constraint violations).

**Q: You encountered a Foreign Key Constraint violation when deleting a Menu Item. How did you fix it?**
**A:** The database prevented deleting a `MenuItem` because existing records in `menu_item_ratings` referenced it. 
- **Fix:** I added `@OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)` to the `MenuItem` entity. This tells JPA to automatically remove the associated children (ratings) when the parent (item) is deleted.

---

## 🧠 Business Logic

**Q: How do you handle price changes for Menu Items? If a price changes, does it affect past orders?**
**A:** No, it does not. In `OrderServiceImpl`, when an order is placed, we explicitly **lock the price** at that moment:
```java
orderItem.setPrice(menuItem.getPrice()); // Locking price at order time
```
We save this snapshot price in the `OrderItem` table. So, even if the `MenuItem` price changes later, the historical order record remains accurate.

**Q: How do you ensure users don't rate the same item twice?**
**A:** In `RatingServiceImpl`, before adding a rating, we check if one already exists using:
```java
menuItemRatingRepository.findByMenuItemIdAndUserId(...)
```
If a rating exists, we either throw an exception (for creating) or update the existing one. We also have a unique constraint in the `MenuItemRating` entity: `@UniqueConstraint(columnNames = {"menu_item_id", "user_id"})` to enforce this at the database level.

**Q: How is transaction management handled?**
**A:** We use the `@Transactional` annotation on our Service classes (e.g., `OrderServiceImpl`). This ensures that complex operations, like saving an order and all its items, happen atomically. If any part fails (e.g., an item is unavailable), the entire transaction rolls back, preventing inconsistent data states.

---

## 🛡️ Best Practices

**Q: How do you handle exceptions?**
**A:** We have a global exception handling strategy (likely utilizing `@ControllerAdvice` or similar, though strictly locally we use custom exceptions/checks). 
- We use `ResourceNotFoundException` when an entity is not found (e.g., `findById().orElseThrow(...)`).
- Validations are handled using Jakarta Validation annotations (`@NotNull`, `@Min`, `@Valid`) in the DTOs and Controllers.

**Q: What HTTP verbs do you use and why?**
**A:**
- **GET**: Retrieving data (Menus, Orders). Safe and idempotent.
- **POST**: Creating resources (Placing Order, Adding Restaurant). Not idempotent.
- **PUT**: Updating resources (Updating Menu Item). Idempotent.
- **DELETE**: Removing resources (Deleting Category).
