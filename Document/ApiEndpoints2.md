# 🍽️ Online Food Ordering System - API Documentation

This document provides a comprehensive list of all API endpoints available in the Online Food Ordering System. The APIs are categorized into **Management APIs** (for restaurant owners/admins) and **User APIs** (for customers).

## 🏢 Management APIs
*Base Path:* `/api/v1/management`

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/restaurants` | Create a new Restaurant |
| **PUT** | `/restaurants/{id}` | Update an existing Restaurant |
| **POST** | `/restaurants/{restaurantId}/categories` | Create a new Menu Category for a Restaurant |
| **PUT** | `/categories/{categoryId}` | Update an existing Menu Category |
| **POST** | `/categories/{categoryId}/items` | Create a new Menu Item in a Category |
| **PUT** | `/items/{itemId}` | Update an existing Menu Item |

---

## 👤 User APIs
*Base Path:* `/api/v1`

### 🏪 Restaurant & Menu
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **GET** | `/restaurants` | Get all Restaurants |
| **GET** | `/restaurants/{restaurantId}` | Get details of a specific Restaurant |
| **GET** | `/restaurants/{restaurantId}/categories` | Get all Menu Categories for a Restaurant |
| **GET** | `/categories/{categoryId}/items` | Get all Menu Items in a Category |

### 🛍️ Orders
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/orders` | Place a new Order |
| **GET** | `/orders/{orderId}` | Get details of a specific Order |
| **GET** | `/users/{userId}/orders` | Get all Orders placed by a specific User |

### ⭐ Ratings
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **POST** | `/ratings` | Add a Rating to a Menu Item |
| **PUT** | `/ratings` | Update an existing Rating for a Menu Item |
