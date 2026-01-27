import {Link} from 'react-router-dom'

function RestaurantCard({restaurant}) {
  return (
    <div className="card h-100 text-center" style={{width: "18rem"}}>
        <img src = {restaurant.imageUrl} className="card-img-top mx-auto mt-3" alt="..."/>
        <div className="card-body">
            <h5 className="card-title">{restaurant.name}</h5>
            <p className="card-text">{restaurant.description}</p>
            <Link to={`/restaurants/${restaurant.id}`} className="btn btn-outline-danger">Visit Us</Link>
        </div>
    </div>
  )
}

export default RestaurantCard