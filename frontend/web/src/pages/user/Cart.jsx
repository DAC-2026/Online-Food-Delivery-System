import { useDispatch, useSelector } from "react-redux";
import {Link} from 'react-router-dom'
import {
  addItem,
  removeItem,
  clearCart,
  selectCartTotal,

} from "../../store/cartSlice";

function Cart() {
  const dispatch = useDispatch();

  const cartItems = useSelector(state=>state.cart.items);
  const totalAmount = useSelector(selectCartTotal);

  const itemsArray = Object.values(cartItems);

  if (itemsArray.length === 0) {
    return (
      <div className="container mt-5 text-center">
        <h3>Your cart is empty 🛒</h3>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <h2 className="mb-4">My Cart</h2>

      {itemsArray.map((item) => (
        <div
          key={item.id}
          className="card mb-3 shadow-sm"
        >
          <div className="row g-0 align-items-center">
            <div className="col-md-2 text-center">
              <img
                src={item.imageUrl}
                alt={item.name}
                className="img-fluid rounded"
                style={{ maxHeight: "80px" }}
              />
            </div>

            <div className="col-md-4">
              <div className="card-body">
                <h5 className="card-title mb-1">
                  {item.name}
                </h5>
                <p className="text-muted mb-0">
                  ₹{item.price} each
                </p>
              </div>
            </div>

            <div className="col-md-3 text-center">
              <div className="d-flex justify-content-center align-items-center gap-2">
                <button
                  className="btn btn-danger"
                  onClick={() => dispatch(removeItem(item.id))}
                >
                  −
                </button>

                <span className="fw-bold">
                  {item.quantity}
                </span>

                <button
                  className="btn btn-danger"
                  onClick={() =>
                    dispatch(
                      addItem({
                        id: item.id,
                        name: item.name,
                        price: item.price,
                        imageUrl: item.imageUrl,
                        isVeg: item.isVeg,
                      })
                    )
                  }
                >
                  +
                </button>
              </div>
            </div>

            <div className="col-md-3 text-end pe-4">
              <strong>
                ₹{item.price * item.quantity}
              </strong>
            </div>
          </div>
        </div>
      ))}

      {/* CART SUMMARY */}
      <div className="card mt-4 p-3 shadow">
        <div className="d-flex justify-content-between align-items-center">
          <h4>Total</h4>
          <h4>₹{totalAmount}</h4>
        </div>

        <div className="d-flex justify-content-between mt-3">
          <button
            className="btn btn-outline-danger"
            onClick={() => dispatch(clearCart())}
          >
            Clear Cart
          </button>

          <Link to = "/checkout" className="btn btn-outline-success">Checkout</Link>
        </div>
      </div>
    </div>
  );
}

export default Cart;
