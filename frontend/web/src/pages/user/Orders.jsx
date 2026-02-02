import { useEffect, useState } from "react";
import { getUserOrders } from "../../api/orderApi";
// import { Link } from "react-router-dom";

export default function Orders() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Hardcoded userId as per requirement
  const userId = 1;

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await getUserOrders(userId);
        setOrders(response.data);
      } catch (err) {
        console.error("Error fetching orders:", err);
        setError("Failed to load orders. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchOrders();
  }, [userId]);

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
      <div className="container mt-5">
        <div className="alert alert-danger" role="alert">
          {error}
        </div>
      </div>
    );
  }

  return (
    <div className="container mt-4 mb-5">
      <h2 className="mb-4 text-center fw-bold">My Orders</h2>
      {orders.length === 0 ? (
        <div className="text-center">
          <p className="lead">You haven't placed any orders yet.</p>
        </div>
      ) : (
        <div className="row">
          {orders.map((order) => (
            <div key={order.orderId} className="col-12 mb-4">
              <div className="card shadow-sm">
                <div className="card-header d-flex justify-content-between align-items-center bg-light">
                  <span className="fw-bold">Order #{order.orderId}</span>
                  <span
                    className={`badge ${order.orderStatus === "CONFIRMED"
                      ? "bg-success"
                      : order.orderStatus === "CANCELLED"
                        ? "bg-danger"
                        : "bg-warning text-dark"
                      }`}
                  >
                    {order.orderStatus}
                  </span>
                </div>
                <div className="card-body">
                  <div className="row mb-3">
                    <div className="col-md-6">
                      <p className="mb-1"><strong>Payment Mode:</strong> {order.paymentMode}</p>
                      <p className="mb-1">
                        <strong>Payment Status:</strong>{" "}
                        <span className={order.paymentStatus === "COMPLETED" ? "text-success" : "text-danger"}>
                          {order.paymentStatus}
                        </span>
                      </p>
                    </div>
                    <div className="col-md-6 text-md-end">
                      <h5 className="mb-0">Total: ₹{order.totalAmount}</h5>
                    </div>
                  </div>

                  <h6 className="border-bottom pb-2">Items</h6>
                  <ul className="list-group list-group-flush">
                    {order.items.map((item, index) => (
                      <li key={index} className="list-group-item d-flex justify-content-between align-items-center">
                        <div>
                          <span className="fw-semibold">{item.menuItemName}</span>
                          <span className="text-muted ms-2">x {item.quantity}</span>
                        </div>
                        <span>₹{item.price * item.quantity}</span>
                      </li>
                    ))}
                  </ul>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
