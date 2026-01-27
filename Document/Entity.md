# 📘 Entity Design Documentation  
## Online Food Delivery System (Backend)

---

## 🎯 Design Philosophy

The database is designed to be **normalized**, **scalable**, and **frontend-friendly**.  
Temporary or session-based data (such as cart) is handled on the frontend, while the backend stores only **business-critical and persistent data** like users, restaurants, menus, and orders.

This approach ensures:
- Stateless backend
- Clean separation of concerns
- Easy scalability and maintainability

---

## 1️⃣ User Entity (`users`)

### Purpose  
Represents an end user who can browse restaurants and place orders.

### Key Attributes
- `user_id` (Primary Key)
- `name`
- `email` (unique)
- `phone`
- `password`
- `auth_provider`
- `created_at`
- `updated_at`

### Relationships
- One user → Many addresses
- One user → Many orders

### Design Decisions (Why)
- `email` is unique to avoid duplicate accounts.
- `auth_provider` enables support for multiple authentication mechanisms (Manual, Google, etc.).
- User entity is kept authentication-agnostic for future OAuth integration.

### Interview Explanation
> The User entity is designed to support both manual and OAuth-based authentication without redesigning the schema.

---

## 2️⃣ Address Entity (`address`)

### Purpose  
Stores delivery addresses associated with users.

### Key Attributes
- `address_id` (Primary Key)
- `address_line`
- `city`
- `pincode`
- `latitude`
- `longitude`
- `label` (Home, Work)
- `user_id` (Foreign Key)

### Relationships
- Many addresses → One user
- One address → Many orders

### Design Decisions
- Address is separated to avoid duplicating address details across multiple orders.
- Latitude and longitude are stored to support future distance-based delivery features.

### Interview Explanation
> Address is normalized to reduce redundancy and enable geolocation-based enhancements.

---

## 3️⃣ Restaurant Entity (`restaurant`)

### Purpose  
Represents restaurants available on the platform.

### Key Attributes
- `restaurant_id` (Primary Key)
- `name`
- `description`
- `rating`
- `availability_status`
- `image_url`
- `created_at`
- `updated_at`

### Relationships
- One restaurant → One restaurant address
- One restaurant → Many menu categories
- One restaurant → Many orders

### Design Decisions
- `availability_status` is used instead of a boolean to support multiple states (OPEN, CLOSED, TEMPORARILY_CLOSED).
- Image URL is stored instead of image data, following industry best practices.
- Rating is stored to optimize sorting and filtering.

### Interview Explanation
> Restaurant is optimized for fast read operations required by listing and search APIs.

---

## 4️⃣ RestaurantAddress Entity (`restaurant_address`) – Weak Entity

### Purpose  
Stores the fixed physical address of a restaurant.

### Key Attributes
- `restaurant_id` (Primary Key + Foreign Key)
- `address_line`
- `city`
- `pincode`
- `latitude`
- `longitude`

### Relationships
- One-to-One with Restaurant

### Design Decisions
- Each restaurant has exactly one address, making this a weak entity.
- The entity cannot exist independently without a restaurant.

### Interview Explanation
> RestaurantAddress is modeled as a weak entity because it depends entirely on the Restaurant entity.

---

## 5️⃣ MenuCategory Entity (`menu_category`)

### Purpose  
Groups menu items into logical categories such as Starters, Main Course, or Desserts.

### Key Attributes
- `category_id` (Primary Key)
- `name`
- `description`
- `image_url`
- `restaurant_id` (Foreign Key)

### Relationships
- One restaurant → Many categories
- One category → Many menu items

### Design Decisions
- Category is separated to avoid repeating category names in menu items.
- Enables category-based filtering and sorting without string comparisons.

### Interview Explanation
> This design improves normalization and simplifies category-based queries.

---

## 6️⃣ MenuItem Entity (`menu_item`)

### Purpose  
Represents food items that users can order.

### Key Attributes
- `id` (Primary Key)
- `name`
- `description`
- `price`
- `rating`
- `is_veg`
- `is_available`
- `preparation_time`
- `image_url`
- `category_id` (Foreign Key)

### Relationships
- Many menu items → One category
- One menu item → Many order items

### Design Decisions
- `is_available` allows disabling items without deleting data.
- Price is stored as decimal for accuracy.
- Image URL is stored for frontend rendering.

### Interview Explanation
> MenuItem is designed for high-read performance and UI-driven APIs.

---

## 7️⃣ CustomerOrder Entity (`customer_orders`)

### Purpose  
Stores finalized orders placed by users.

### Key Attributes
- `order_id` (Primary Key)
- `total_amount`
- `order_status`
- `payment_status`
- `payment_mode`
- `created_at`
- `updated_at`
- `user_id` (Foreign Key)
- `restaurant_id` (Foreign Key)
- `address_id` (Foreign Key)

### Relationships
- Many orders → One user
- Many orders → One restaurant
- One order → Many order items

### Design Decisions
- Cart is not stored in backend to keep the system stateless.
- Only final orders are persisted.
- Enums are used for clean state management.

### Interview Explanation
> Persisting only finalized orders keeps the backend stateless and scalable.

---

## 8️⃣ OrderItem Entity (`order_items`)

### Purpose  
Stores individual menu items within an order.

### Key Attributes
- `order_item_id` (Primary Key)
- `quantity`
- `price`
- `order_id` (Foreign Key)
- `menu_item_id` (Foreign Key)

### Relationships
- Many order items → One order
- Many order items → One menu item

### Design Decisions
- Price is stored as a snapshot to preserve historical accuracy.
- Decouples order data from future menu price changes.

### Interview Explanation
> OrderItem preserves historical correctness even if menu prices change later.

---

## 🧠 Final Interview Summary

> The system follows strict normalization, avoids storing session-based data like cart, supports future scalability, and mirrors real-world food delivery platform design principles.
