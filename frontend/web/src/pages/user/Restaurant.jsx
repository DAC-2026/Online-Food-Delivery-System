import {useState, useEffect} from 'react'
import  {getAllRestaurant}  from '../../api/restaurantApi';
import RestaurantCard from '../../components/RestaurantCard'

function Restaurant() {
  const [restaurants, setRestaurants] = useState([]);
  useEffect(()=>{
    getAllRestaurant()
    .then(response=>{
      setRestaurants(response.data)
      console.log(response.data)
    })
    .catch((error)=>{
      console.error("Error while fetching", error)
    });
  },[])
  return (
    <div className='container mt-4'>
      <h2>Restaurants</h2>
      
      <ul  className="d-flex flex-wrap gap-4">
        {restaurants.map(restaurant=>(
            <RestaurantCard key = {restaurant.id} restaurant = {restaurant}/>
        ))}
      </ul>
      {restaurants.length == 0 && <p>Restaurant not found</p>}
    </div>
  )
}

export default Restaurant