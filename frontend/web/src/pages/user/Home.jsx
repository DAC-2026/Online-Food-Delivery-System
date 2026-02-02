import { useEffect, useState } from "react";
import { getAllMenuItems } from "../../api/itemApi";
// import MenuItemCard from "../../components/MenuItemCard"; // Assuming a card exists, or reusing logic
// import { Link } from "react-router-dom";

function Home() {
    const [menuItems, setMenuItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchItems = async () => {
            try {
                const response = await getAllMenuItems();
                setMenuItems(response.data);
            } catch (err) {
                console.error("Error fetching menu items:", err);
                setError("Failed to load menu items.");
            } finally {
                setLoading(false);
            }
        };

        fetchItems();
    }, []);

    if (loading) {
        return (
            <div className="container mt-5 text-center">
                <div className="spinner-border text-primary" role="status">
                    <span className="visually-hidden">Loading...</span>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="container mt-5 text-center">
                <div className="alert alert-danger" role="alert">
                    {error}
                </div>
            </div>
        );
    }

    return (
        <div className="container mt-4 mb-5">
            <div className="text-center mb-5">
                <h1 className="display-4 fw-bold">Welcome to Foodie</h1>
                <p className="lead text-muted">Browse our delicious menu and order online!</p>
            </div>

            <div className="row row-cols-1 row-cols-md-3 g-4">
                {menuItems.map((item) => (
                    <div key={item.id} className="col">
                        <div className="card h-100 shadow-sm">
                            {/* Placeholder image if not available, or use item.imageUrl if backend provides it */}
                            <div className="bg-light d-flex align-items-center justify-content-center" style={{ height: "200px" }}>
                                <span className="text-muted fs-1">🍔</span>
                            </div>
                            <div className="card-body">
                                <h5 className="card-title fw-bold">{item.name}</h5>
                                <p className="card-text text-muted text-truncate">{item.description}</p>
                                <div className="d-flex justify-content-between align-items-center mt-3">
                                    <span className="fs-5 fw-bold">₹{item.price}</span>
                                    <button className="btn btn-outline-danger btn-sm">Add to Cart</button>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            {menuItems.length === 0 && (
                <div className="text-center mt-5">
                    <p className="lead">No menu items found.</p>
                </div>
            )}
        </div>
    );
}

export default Home;