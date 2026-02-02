import { useState, useEffect } from "react";
import { createRestaurant, createCategory, createMenuItem, uploadImageToCloudinary } from "../../api/adminApi";
import { getAllRestaurant } from "../../api/restaurantApi"; // Need to fetch restaurants for dropdowns
import { getItemByRestaurantId } from "../../api/itemApi"; // Assuming we can use this or need a new one for categories
import { baseUrl } from "../../utils/constants";
import axios from "axios";

export default function AdminDashboard() {
    const [activeTab, setActiveTab] = useState("restaurant");
    const [message, setMessage] = useState({ text: "", type: "" });
    const [loading, setLoading] = useState(false);

    // Data for Dropdowns
    const [restaurants, setRestaurants] = useState([]);
    const [categories, setCategories] = useState([]);

    // Forms State
    const [restaurantForm, setRestaurantForm] = useState({
        name: "",
        description: "",
        rating: "",
        availabilityStatus: "OPEN",
        imageFile: null,
    });

    const [categoryForm, setCategoryForm] = useState({
        restaurantId: "",
        name: "",
        description: "",
        imageFile: null,
    });

    const [menuItemForm, setMenuItemForm] = useState({
        restaurantId: "",
        categoryId: "",
        name: "",
        description: "",
        price: "",
        rating: "",
        preparationTime: "",
        isVeg: true,
        isAvailable: true,
        imageFile: null,
    });

    // Fetch Restaurants on Mount
    useEffect(() => {
        fetchRestaurants();
    }, []);

    const fetchRestaurants = async () => {
        try {
            const res = await getAllRestaurant();
            setRestaurants(res.data);
        } catch (err) {
            console.error("Error fetching restaurants", err);
        }
    };

    // Fetch Categories when MenuItem's Restaurant changes
    useEffect(() => {
        if (menuItemForm.restaurantId) {
            fetchCategories(menuItemForm.restaurantId);
        }
    }, [menuItemForm.restaurantId]);

    const fetchCategories = async (restaurantId) => {
        try {
            // We need an endpoint to get categories by restaurant. 
            // Existing MenuController has: GET /{restaurantId}/categories
            const res = await axios.get(`${baseUrl}/${restaurantId}/categories`);
            setCategories(res.data);
        } catch (err) {
            console.error("Error fetching categories", err);
            setCategories([]);
        }
    }

    // Generic File Handler
    const handleFileChange = (e, setForm) => {
        setForm((prev) => ({ ...prev, imageFile: e.target.files[0] }));
    };

    // Generic Text Handler
    const handleChange = (e, setForm) => {
        const { name, value, type, checked } = e.target;
        setForm((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };

    const showMessage = (text, type) => {
        setMessage({ text, type });
        setTimeout(() => setMessage({ text: "", type: "" }), 5000);
    };

    // --- SUBMIT HANDLERS ---

    const handleRestaurantSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            let imageUrl = "";
            if (restaurantForm.imageFile) {
                imageUrl = await uploadImageToCloudinary(restaurantForm.imageFile);
            }

            await createRestaurant({
                name: restaurantForm.name,
                description: restaurantForm.description,
                rating: restaurantForm.rating,
                availabilityStatus: restaurantForm.availabilityStatus,
                imageUrl: imageUrl,
            });

            showMessage("Restaurant created successfully!", "success");
            setRestaurantForm({ name: "", description: "", rating: "", availabilityStatus: "OPEN", imageFile: null });
            fetchRestaurants(); // Refresh list
        } catch (err) {
            showMessage("Failed to create restaurant.", "danger");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleCategorySubmit = async (e) => {
        e.preventDefault();
        if (!categoryForm.restaurantId) {
            showMessage("Please select a restaurant.", "danger");
            return;
        }
        setLoading(true);
        try {
            let imageUrl = "";
            if (categoryForm.imageFile) {
                imageUrl = await uploadImageToCloudinary(categoryForm.imageFile);
            }

            await createCategory(categoryForm.restaurantId, {
                name: categoryForm.name,
                description: categoryForm.description,
                imageUrl: imageUrl,
            });

            showMessage("Category created successfully!", "success");
            setCategoryForm({ ...categoryForm, name: "", description: "", imageFile: null });
        } catch (err) {
            showMessage("Failed to create category.", "danger");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleMenuItemSubmit = async (e) => {
        e.preventDefault();
        if (!menuItemForm.categoryId) {
            showMessage("Please select a category.", "danger");
            return;
        }
        setLoading(true);
        try {
            let imageUrl = "";
            if (menuItemForm.imageFile) {
                imageUrl = await uploadImageToCloudinary(menuItemForm.imageFile);
            }

            await createMenuItem(menuItemForm.categoryId, {
                name: menuItemForm.name,
                description: menuItemForm.description,
                price: menuItemForm.price,
                rating: menuItemForm.rating,
                preparationTime: menuItemForm.preparationTime,
                isVeg: menuItemForm.isVeg,
                isAvailable: menuItemForm.isAvailable,
                imageUrl: imageUrl,
            });

            showMessage("Menu Item created successfully!", "success");
            setMenuItemForm({ ...menuItemForm, name: "", description: "", price: "", rating: "", preparationTime: "", imageFile: null });
        } catch (err) {
            showMessage("Failed to create menu item.", "danger");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container mt-5 mb-5">
            <h2 className="mb-4 fw-bold">Admin Dashboard</h2>

            {message.text && (
                <div className={`alert alert-${message.type}`} role="alert">
                    {message.text}
                </div>
            )}

            <ul className="nav nav-tabs mb-4">
                <li className="nav-item">
                    <button className={`nav-link ${activeTab === "restaurant" ? "active" : ""}`} onClick={() => setActiveTab("restaurant")}>
                        Add Restaurant
                    </button>
                </li>
                <li className="nav-item">
                    <button className={`nav-link ${activeTab === "category" ? "active" : ""}`} onClick={() => setActiveTab("category")}>
                        Add Category
                    </button>
                </li>
                <li className="nav-item">
                    <button className={`nav-link ${activeTab === "item" ? "active" : ""}`} onClick={() => setActiveTab("item")}>
                        Add Menu Item
                    </button>
                </li>
            </ul>

            <div className="card shadow-sm p-4">
                {/* RESTAURANT FORM */}
                {activeTab === "restaurant" && (
                    <form onSubmit={handleRestaurantSubmit}>
                        <div className="mb-3">
                            <label className="form-label">Restaurant Name</label>
                            <input type="text" className="form-control" name="name" value={restaurantForm.name} onChange={(e) => handleChange(e, setRestaurantForm)} required />
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Description</label>
                            <textarea className="form-control" name="description" value={restaurantForm.description} onChange={(e) => handleChange(e, setRestaurantForm)} required />
                        </div>
                        <div className="row">
                            <div className="col-md-6 mb-3">
                                <label className="form-label">Rating (0-5)</label>
                                <input type="number" step="0.1" min="0" max="5" className="form-control" name="rating" value={restaurantForm.rating} onChange={(e) => handleChange(e, setRestaurantForm)} required />
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="form-label">Status</label>
                                <select className="form-select" name="availabilityStatus" value={restaurantForm.availabilityStatus} onChange={(e) => handleChange(e, setRestaurantForm)}>
                                    <option value="OPEN">OPEN</option>
                                    <option value="CLOSED">CLOSED</option>
                                    <option value="TEMPORARILY_CLOSED">TEMPORARILY CLOSED</option>
                                </select>
                            </div>
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Image</label>
                            <input type="file" className="form-control" accept="image/*" onChange={(e) => handleFileChange(e, setRestaurantForm)} required />
                        </div>
                        <button type="submit" className="btn btn-primary" disabled={loading}>{loading ? "Saving..." : "Create Restaurant"}</button>
                    </form>
                )}

                {/* CATEGORY FORM */}
                {activeTab === "category" && (
                    <form onSubmit={handleCategorySubmit}>
                        <div className="mb-3">
                            <label className="form-label">Select Restaurant</label>
                            <select className="form-select" name="restaurantId" value={categoryForm.restaurantId} onChange={(e) => handleChange(e, setCategoryForm)} required>
                                <option value="">-- Select Restaurant --</option>
                                {restaurants.map(r => <option key={r.id} value={r.id}>{r.name}</option>)}
                            </select>
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Category Name</label>
                            <input type="text" className="form-control" name="name" value={categoryForm.name} onChange={(e) => handleChange(e, setCategoryForm)} required />
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Description</label>
                            <textarea className="form-control" name="description" value={categoryForm.description} onChange={(e) => handleChange(e, setCategoryForm)} required />
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Image</label>
                            <input type="file" className="form-control" accept="image/*" onChange={(e) => handleFileChange(e, setCategoryForm)} required />
                        </div>
                        <button type="submit" className="btn btn-primary" disabled={loading}>{loading ? "Saving..." : "Create Category"}</button>
                    </form>
                )}

                {/* MENU ITEM FORM */}
                {activeTab === "item" && (
                    <form onSubmit={handleMenuItemSubmit}>
                        <div className="mb-3">
                            <label className="form-label">Select Restaurant</label>
                            <select className="form-select" name="restaurantId" value={menuItemForm.restaurantId} onChange={(e) => handleChange(e, setMenuItemForm)} required>
                                <option value="">-- Select Restaurant --</option>
                                {restaurants.map(r => <option key={r.id} value={r.id}>{r.name}</option>)}
                            </select>
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Select Category</label>
                            <select className="form-select" name="categoryId" value={menuItemForm.categoryId} onChange={(e) => handleChange(e, setMenuItemForm)} required disabled={!menuItemForm.restaurantId}>
                                <option value="">-- Select Category --</option>
                                {categories.map(c => <option key={c.categoryId} value={c.categoryId}>{c.name}</option>)}
                            </select>
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Item Name</label>
                            <input type="text" className="form-control" name="name" value={menuItemForm.name} onChange={(e) => handleChange(e, setMenuItemForm)} required />
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Description</label>
                            <textarea className="form-control" name="description" value={menuItemForm.description} onChange={(e) => handleChange(e, setMenuItemForm)} required />
                        </div>
                        <div className="row">
                            <div className="col-md-6 mb-3">
                                <label className="form-label">Price</label>
                                <input type="number" step="0.01" className="form-control" name="price" value={menuItemForm.price} onChange={(e) => handleChange(e, setMenuItemForm)} required />
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="form-label">Rating</label>
                                <input type="number" step="0.1" min="0" max="5" className="form-control" name="rating" value={menuItemForm.rating} onChange={(e) => handleChange(e, setMenuItemForm)} />
                            </div>
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Preparation Time (mins)</label>
                            <input type="number" className="form-control" name="preparationTime" value={menuItemForm.preparationTime} onChange={(e) => handleChange(e, setMenuItemForm)} required />
                        </div>
                        <div className="mb-3 form-check">
                            <input type="checkbox" className="form-check-input" id="isVeg" name="isVeg" checked={menuItemForm.isVeg} onChange={(e) => handleChange(e, setMenuItemForm)} />
                            <label className="form-check-label" htmlFor="isVeg">Is Vegetarian</label>
                        </div>
                        <div className="mb-3 form-check">
                            <input type="checkbox" className="form-check-input" id="isAvailable" name="isAvailable" checked={menuItemForm.isAvailable} onChange={(e) => handleChange(e, setMenuItemForm)} />
                            <label className="form-check-label" htmlFor="isAvailable">Is Available</label>
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Image</label>
                            <input type="file" className="form-control" accept="image/*" onChange={(e) => handleFileChange(e, setMenuItemForm)} required />
                        </div>
                        <button type="submit" className="btn btn-primary" disabled={loading}>{loading ? "Saving..." : "Create Menu Item"}</button>
                    </form>
                )}
            </div>
        </div>
    );
}
