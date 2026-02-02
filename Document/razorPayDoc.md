# Razorpay Integration Technical Guide

This document explains the "Verify-First" integration of Razorpay into the Online Food Ordering System. It details the code changes, data flow, and API contracts used to ensure secure payments.

---

## 1. Architecture & Data Flow
**Why this approach?**
To prevent "Ghost Orders" (unpaid orders in the database) and ensure security, we decoupled the **Payment Step** from the **Order Creation Step**.

**The Flow:**
1.  **Frontend (`Checkout.jsx`)**: Collects User & Cart details -> Asks Backend to **Initiate** payment.
2.  **Backend (`PaymentController`)**: Calculates amount -> Calls Razorpay -> Returns `razorpay_order_id`.
3.  **Frontend**: Opens Razorpay Modal -> User Pays -> Razorpay returns `payment_id` & `signature`.
4.  **Frontend**: Sends original Order Data + Payment Details to Backend (`placeOrder`).
5.  **Backend (`OrderService`)**:
    *   Verifies Signature (Security Check).
    *   **Only IF Valid**: Inserts data into `CustomerOrder` and `OrderItem` tables.

---

## 2. Frontend Implementation (`Checkout.jsx`)

**Role**: The coordinator. It decides whether to go to Razorpay (Online) or directly to the server (COD).

### Key Logic: `handleOnlineOrder`
This function manages the 3-step online payment process.

```javascript
// file: frontend/web/src/components/checkout/Checkout.jsx

const handleOnlineOrder = async (orderPayload) => {
    // Step 1: Initiate Payment (No DB save yet)
    // We send the order payload so the backend can calculate the exact amount.
    const response = await createRazorpayOrder(orderPayload);
    const razorpayOrderId = response.data; // e.g., "order_Kz123..."

    const options = {
        key: "rzp_test_...", // Your Public Key
        currency: "INR",
        name: "Foodies",
        order_id: razorpayOrderId, // Binds the UI to the backend order
        handler: async function (response) {
            // Step 2: User Paid Successfully. Now we Place the Order.
            try {
                const finalPayload = {
                    ...orderPayload, // Original Cart Items & Address
                    // Add the Payment Proofs
                    razorpayOrderId: response.razorpay_order_id,
                    razorpayPaymentId: response.razorpay_payment_id,
                    razorpaySignature: response.razorpay_signature
                };

                // Step 3: Final Backend Call to Save Data
                const orderResponse = await placeOrder(finalPayload);
                window.alert(`Order #${orderResponse.data.orderId} placed!`);
            } catch (error) {
                console.error("Payment success, but Order Save failed", error);
            }
        },
        // ... theme & prefill options
    };

    const razorpay = new window.Razorpay(options);
    razorpay.open();
};
```

---

## 3. Backend: Initiating the Order

**Endpoint**: `POST /api/v1/payment/create-order`
**File**: `PaymentController.java`

This endpoint is responsible for communicating with Razorpay to generate a trusted Order ID. It does **not** save anything to the local database.

### Request Body (JSON)
The frontend sends the full order intent so the backend can calculate the price.
```json
{
  "userId": 1,
  "deliveryAddressId": 5,
  "paymentMode": "UPI",
  "items": [
    { "menuItemId": 101, "quantity": 2 },
    { "menuItemId": 102, "quantity": 1 }
  ]
}
```

### Implementation
```java
// PaymentController.java
@PostMapping("/create-order")
public ResponseEntity<?> createRazorpayOrder(@RequestBody PlaceOrderRequestDto request) {
    // 1. Calculate confirmed total from DB (Security)
    BigDecimal totalAmount = orderService.calculateTotalOrderAmount(request);
    
    // 2. call Razorpay API
    String razorpayOrderId = paymentService.createRazorpayOrder(totalAmount);
    
    // 3. Return ID to frontend
    return ResponseEntity.ok(razorpayOrderId);
}
```

**Response**: `order_N7sl1290s...` (String)

---

## 4. Backend: Verifying & Saving (`OrderServiceImpl.java`)

**Endpoint**: `POST /api/v1/orders`
**File**: `OrderServiceImpl.java`

This is where the actual data persistence happens. This method is used for BOTH **COD** and **Online** orders, but it has a special check for Online.

### Logic Flow (`placeOrder`)
1.  **Calculate Total**: Re-calculates total from DB prices (never trust the frontend).
2.  **Min Amount Check**: Rejects if `< 1 INR`.
3.  **Signature Verification (Crucial Step)**:
    If `paymentMode != COD`, it takes the `razorpaySignature` from the request and re-computes the HMAC-SHA256 hash using your `key_secret`. If the hash doesn't match, it throws an error.
4.  **Save to Database**:
    Only executes if steps 1-3 pass.

### Implementation Snippet
```java
// OrderServiceImpl.java

public OrderResponse placeOrder(PlaceOrderRequestDto request) {
    // ... Calculate Total Amount ...

    // SECURITY CHECK
    if (request.getPaymentMode() != PaymentMode.COD) {
        boolean isValid = paymentService.verifyPaymentSignature(
            request.getRazorpayOrderId(),
            request.getRazorpayPaymentId(),
            request.getRazorpaySignature()
        );

        if (!isValid) {
            throw new RuntimeException("Invalid Payment Signature!"); 
            // Stops execution. No data saved to DB.
        }
    }

    // CREATE DB RECORD
    CustomerOrder order = new CustomerOrder();
    order.setOrderStatus(OrderStatus.CONFIRMED);
    
    if (request.getPaymentMode() != PaymentMode.COD) {
        order.setPaymentStatus(PaymentStatus.COMPLETED); // Instant Success
        order.setRazorpayOrderId(request.getRazorpayOrderId());
    } else {
        order.setPaymentStatus(PaymentStatus.PENDING); // COD
    }

    // ... Save Order & OrderItems ...
    return mapToOrderResponse(savedOrder);
}
```

### Request Body (Final `placeOrder` call)
```json
{
  "userId": 1,
  "deliveryAddressId": 5,
  "paymentMode": "UPI",
  "items": [...],
  "razorpayOrderId": "order_N7sl...",
  "razorpayPaymentId": "pay_29...",
  "razorpaySignature": "a1b2c3d4..."  <-- The Proof
}
```

---

## 5. Database Impact

The `CustomerOrder` and `OrderItem` tables are **only** populated in Step 4.

*   **Failed Payment**: If the user closes the popup or payment fails -> Frontend never calls `placeOrder` -> **Database remains empty**.
*   **Success**:
    *   `CustomerOrder` row created with `payment_status = 'COMPLETED'` and `razorpay_order_id`.
    *   `OrderItem` rows created linking to that order.

This ensures your database is always clean and accurate.
