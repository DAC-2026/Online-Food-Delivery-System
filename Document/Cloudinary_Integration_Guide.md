# Cloudinary Image Upload Integration Guide

This document explains how we implemented image uploads using **Cloudinary** in our React application. It is designed for developers encountering this code for the first time.

## 1. Overview
Instead of uploading images directly to our Java backend (which consumes server bandwidth and requires complex file handling), we upload images **directly from the browser (Frontend)** to **Cloudinary**.
Cloudinary then gives us a public **URL** (link) to that image. We simply send that **URL** to our backend to save it in the database.

**Flow:**
`User Selects File` -> `React uploads to Cloudinary` -> `Cloudinary returns URL` -> `React sends URL + Data to Backend`

## 2. Prerequisites & Setup
To make this work, we configured the following in the Cloudinary Dashboard:
1.  **Cloud Name**: The unique identifier for our account (e.g., `dininsfmi`).
2.  **Upload Preset**: A rule set that tells Cloudinary "allow this upload without a login session".
    *   **Mode**: Must be set to **Unsigned** (crucial for frontend uploads).
    *   **Name**: We named ours `ufinderCloudinary`.

## 3. Configuration (.env)
We store sensitive (or environment-specific) keys in a `.env` file in the frontend so they aren't hardcoded.

**File:** `frontend/web/.env`
```properties
VITE_CLOUDINARY_CLOUD_NAME=dininsfmi
VITE_CLOUDINARY_UPLOAD_PRESET=ufinderCloudinary
```

## 4. The Code Implementation

### Step A: The Uploader Function (`adminApi.js`)
This is the core helper function that talks to Cloudinary.

**File:** `src/api/adminApi.js`
```javascript
export const uploadImageToCloudinary = async (file) => {
    // 1. Get FormData ready (It's like an invisible HTML form)
    const formData = new FormData();
    
    // 2. Attach the file user selected
    formData.append("file", file);
    
    // 3. Attach the 'key' (Upload Preset) that grants us permission
    formData.append("upload_preset", import.meta.env.VITE_CLOUDINARY_UPLOAD_PRESET);
    
    // 4. (Optional) Specify a folder name in Cloudinary
    formData.append("folder", "OnlineFoodOrderingSystem");

    // 5. Build the API URL using our Cloud Name
    const cloudName = import.meta.env.VITE_CLOUDINARY_CLOUD_NAME;
    const url = `https://api.cloudinary.com/v1_1/${cloudName}/image/upload`;

    // 6. Send POST request
    const response = await axios.post(url, formData);

    // 7. Return just the secure (https) URL of the uploaded image
    return response.data.secure_url;
};
```

### Step B: The Component Logic (`AdminDashboard.jsx`)
Here is how we use that function when the user submits a form.

```javascript
const handleRestaurantSubmit = async (e) => {
    e.preventDefault(); // Stop page reload
    
    try {
        let imageUrl = "";
        
        // 1. Check if user selected a file
        if (restaurantForm.imageFile) {
            // 2. WAIT for the image to upload to Cloudinary
            imageUrl = await uploadImageToCloudinary(restaurantForm.imageFile);
            // At this point, imageUrl is "https://res.cloudinary.com/..."
        }

        // 3. Send the form data + the new Image URL to our Backend
        await createRestaurant({
            name: restaurantForm.name,
            description: restaurantForm.description,
            imageUrl: imageUrl, // We send the string URL, not the file!
            // ... other fields
        });
        
        alert("Success!");
    } catch (err) {
        console.error("Failed", err);
    }
};
```

## 5. Request & Response in Action

Here is exactly what happens on the network when you click "Save".

### Request 1: Frontend -> Cloudinary
**URL:** `https://api.cloudinary.com/v1_1/dininsfmi/image/upload`
**Method:** `POST`
**Body (FormData):**
The browser constructs a multipart form body. It looks logically like this:
```json
{
  "file": (Binary Image Data of burger.png),
  "upload_preset": "ufinderCloudinary",
  "folder": "OnlineFoodOrderingSystem"
}
```

### Response 1: Cloudinary -> Frontend
Cloudinary replies with a huge JSON object. We only care about `secure_url`.
```json
{
  "asset_id": "b5e6d2b...",
  "public_id": "OnlineFoodOrderingSystem/burger_xyz",
  "version": 1738500000,
  "version_id": "...",
  "signature": "...",
  "width": 1024,
  "height": 1024,
  "format": "png",
  "resource_type": "image",
  "created_at": "2026-02-02T19:00:00Z",
  "secure_url": "https://res.cloudinary.com/dininsfmi/image/upload/v1738500000/OnlineFoodOrderingSystem/burger_xyz.png",
  "folder": "OnlineFoodOrderingSystem"
}
```

### Request 2: Frontend -> Our Backend
Now we send the data to our Java server.
**URL:** `http://localhost:8080/api/v1/management/restaurants`
**Method:** `POST`
**Body (JSON):**
```json
{
  "name": "Burger King",
  "description": "Best burgers in town",
  "rating": 4.5,
  "availabilityStatus": "OPEN",
  "imageUrl": "https://res.cloudinary.com/dininsfmi/image/upload/v1738500000/OnlineFoodOrderingSystem/burger_xyz.png"
}
```

**Key Takeaway:** Our backend **never touches the actual image file**. It only stores the **string URL** pointing to Cloudinary.
