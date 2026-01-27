import { useParams } from "react-router-dom";
import { useEffect, useState} from "react";
import {getItemByCategoryId, getItemByRestaurantId} from '../api/itemApi'
// import {CartContext} from '../context/CartContext'
import {useDispatch, useSelector} from 'react-redux'
import {addItem, removeItem} from '../store/cartSlice'
function MenuItems({ restaurantId: propRestaurantId }) {
  const { categoryId } = useParams(); // only exists in /menuItem/:categoryId
  const [menuItems, setMenuItems] = useState([]);
  // const {cartItems, addItem, removeItem} = useContext(CartContext)
  const dispatch = useDispatch();
  const cartItems = useSelector((state)=>state.cart.items);
  useEffect(() => {
    // Case 1: Called from RestaurantDetails
    if (propRestaurantId) {
      getItemByRestaurantId(propRestaurantId)
        .then(res =>{
            setMenuItems(res.data)
            console.log(res.data)
        });
    }

    // Case 2: Opened via /menuItem/:categoryId
    else if (categoryId) {
      getItemByCategoryId(categoryId)
        .then(res => setMenuItems(res.data));
    }

  }, [propRestaurantId, categoryId]);

  return (
    <div className="container mt-4">
  <div className="row g-4"> 
    {menuItems.map(item => {
      const quantity = cartItems?.[item.id]?.quantity || 0;
      return (
      <div key={item.id} className="col-md-3">
        <div className="card h-100 shadow-sm">
  {/* Image */}
  <img
    src={"https://tse3.mm.bing.net/th/id/OIP.I2ZZHOziwUEPLsk7LCXr0gHaG_?pid=Api&P=0&h=180"}
    className="card-img-top"
    alt={item.name}
    style={{ height: "180px", objectFit: "cover" }}
  />

  <div className="card-body d-flex flex-column">

    {/* Name + Veg badge */}
    <div className="d-flex justify-content-between align-items-start">
      <h5 className="card-title">{item.name}</h5>

      <span
        className={`badge ${item.isVeg ? "bg-success" : "bg-danger"}`}
      >
        {item.isVeg ? "Veg" : "Non-Veg"}
      </span>
    </div>

    {/* Rating */}
    <div className="mb-1">
      <span className="badge bg-warning text-dark">
        ⭐ {item.rating}
      </span>
    </div>

    {/* Description */}
    <p className="card-text text-muted small">
      {item.description}
    </p>

    {/* Price */}
    <h6 className="fw-bold mb-3">₹ {item.price}</h6>

    {/* Push buttons to bottom */}
    <div className="mt-auto">
      {quantity === 0 ? (
        <button
          className="btn btn-outline-danger w-100"
          onClick={() => dispatch(addItem({
                id: item.id,
                name: item.name,
                price: item.price,
                imageUrl: item.imageUrl,
                isVeg: item.isVeg,
              }))}
        >
          Add to Cart
        </button>
      ) : (
        <div className="d-flex justify-content-center align-items-center gap-2">
          
          <button
            className="btn btn-danger"
            onClick={() => dispatch(removeItem(item.id))}
          >
            −
          </button>
          <span className="fw-bold">{quantity}</span>
          <button
            className="btn btn-danger"
            onClick={() => dispatch(addItem({
                id: item.id,
                name: item.name,
                price: item.price,
                imageUrl: item.imageUrl,
                isVeg: item.isVeg,
              }))}
          >
            +
          </button>
          
        </div>
      )}
    </div>

  </div>
</div>

      </div>
      )
    })}
  </div>
</div>

  );
}

export default MenuItems;
