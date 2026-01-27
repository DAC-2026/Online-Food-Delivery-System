
---

# 📗 Restaurant API Documentation

Base URL: `/api/v1`

---

## 🔹 Get Restaurant by ID

### **1. Description**

Fetch detailed information of a specific restaurant using its unique identifier.

### **2. Endpoint**

```
GET /api/v1/restaurants/{restaurantId}
```

### **3. Path Parameters**

| Parameter    | Type | Description                         |
| ------------ | ---- | ----------------------------------- |
| restaurantId | Long | Unique identifier of the restaurant |

### **4. Query Parameters**

None

### **5. Request Body**

None

### **6. Success Response**

* **Status Code:** `200 OK`
* **Response Body:** `RestaurantResponse`

### **7. Error Responses**

| Status Code | Reason                                 |
| ----------- | -------------------------------------- |
| `400`       | Invalid restaurant ID (ID must be ≥ 1) |
| `404`       | Restaurant not found                   |

---

## 🔹 Get All Restaurants

### **1. Description**

Fetch a list of all available restaurants for the listing page.

### **2. Endpoint**

```
GET /api/v1/restaurants
```

### **3. Path Parameters**

None

### **4. Query Parameters**

None

### **5. Request Body**

None

### **6. Success Response**

* **Status Code:** `200 OK`
* **Response Body:** List of `RestaurantResponse`

### **7. Error Responses**

| Status Code | Reason               |
| ----------- | -------------------- |
| `404`       | No restaurants found |

---



## 🔹 Get Menu Categories by Restaurant ID

### **1. Description**

Get menu Categories By Restaurant Id.

### **2. Endpoint**

```
GET /api/v1/restaurants/{restaurantId}/categories
```

### **3. Path Parameters**

| Parameter    | Type | Description                         |
| ------------ | ---- | ----------------------------------- |
| restaurantId | Long | Unique identifier of the restaurant |

### **4. Query Parameters**

None

### **5. Request Body**

None

### **6. Success Response**

* **Status Code:** `200 OK`
* **Response Body:** List of `MenuCategoryDto`

### **7. Error Responses**

| Status Code | Reason               |
| ----------- | -------------------- |
| `404`       | Restaurant not found |

---

## 🔹 Get Menu Items by Category ID

### **1. Description**

Get menu items By Category Id.

### **2. Endpoint**

```
GET /api/v1/categories/{categoryId}/items
```

### **3. Path Parameters**

| Parameter  | Type | Description                      |
| ---------- | ---- | -------------------------------- |
| categoryId | Long | Unique identifier of the category|

### **4. Query Parameters**

None

### **5. Request Body**

None

### **6. Success Response**

* **Status Code:** `200 OK`
* **Response Body:** List of `MenuItemDto`

### **7. Error Responses**

| Status Code | Reason             |
| ----------- | ------------------ |
| `404`       | Category not found |

---
