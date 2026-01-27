import { Link } from "react-router-dom";
// import {CartContext} from '../context/CartContext'
import { selectCartCount } from "../store/cartSlice";
import {useSelector} from 'react-redux'
// import {useContext} from 'react'


function NavBar() {
  // const {cartCount} = useContext(CartContext);
  const cartCount = useSelector(selectCartCount);
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-danger">
      <div className="container-fluid">
        <Link className="navbar-brand fw-bold" to="/">
          🍽 Foodie
        </Link>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">

            <li className="nav-item">
              <Link className="nav-link" to="/restaurant">
                Restaurants
              </Link>
            </li>

            <li className="nav-item">
              <Link className="nav-link" to="/orders">
                Orders
              </Link>
            </li>
            <li>
                <Link to="/cart" className="nav-link">
             Cart: {cartCount}
          </Link>
            </li>
          </ul>

          <form className="d-flex" role="search">
                <input className ="form-control me-2" type="search" placeholder="" aria-label="Search"/>
                <button className="btn btn-outline" type="submit" style={{border:" 1px solid black"}}>Search</button>
            </form>
        </div>
      </div>
    </nav>
  );
}

export default NavBar;