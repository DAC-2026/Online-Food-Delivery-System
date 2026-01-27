import {useParams} from 'react-router-dom'
import {useState, useEffect} from 'react'
import {getRestaurantById} from '../../api/restaurantApi'
import CategoryCard from '../../components/CategoryCard'
import MenuItems from '../../components/MenuItemCard';

function RestaurantDetails() {
    const {restaurantId} = useParams();
    const [restaurant, setRestaurant] = useState({});
    useEffect(()=>{
      getRestaurantById(restaurantId)
      .then(response =>{
        setRestaurant(response.data)
        console.log(response.data)
      })
      .catch(error =>{
        // console.log(error)
        console.log(error.response.data)
      });
    },[restaurantId])
  return (
    <div className="container mt-4">
      {/* <h2>Restaurant Details</h2> */}
      <p>Restaurant ID: {restaurantId}</p>
      <h2>{restaurant.name}</h2>
      <h2>{restaurant.description}</h2>
      <h2>{restaurant.rating}</h2>
      <CategoryCard/>
      {/* <MenuItems/> */}
      <MenuItems
      restaurantId={restaurantId}
    />
    </div>
  );
}

export default RestaurantDetails