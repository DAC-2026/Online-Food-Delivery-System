import {useParams} from 'react-router-dom'
import {useState, useEffect} from 'react'
import {getCategoriesByRestaurantId} from '../api/categoryApi'
import {Link} from 'react-router-dom'
function Categories() {
    const {restaurantId} = useParams();
    const [categories, setCategory] = useState([]);
    useEffect(()=>{
        getCategoriesByRestaurantId(restaurantId)
        .then(response=>{
            setCategory(response.data)
        })
        .catch(error=>{
            console.log(error)
        });
    },[restaurantId])
  return (
    <div className="row mt-4">
      {categories.map((category) => (
        <Link to = {`/menuItem/${category.categoryId}`} key={category.categoryId} className="col-md-3 text-center mb-4">
          <img
            src= "https://tse1.mm.bing.net/th/id/OIP.88FcvyyiS8DxuWly8TSmnwHaGe?pid=Api&P=0&h=180"//{category.imageUrl}
            className="img-thumbnail rounded-circle mb-2"
            alt={category.name}
            style={{ width: "120px", height: "120px", objectFit: "cover" }}
          />
          <h6 className="fw-semibold">{category.name} </h6>
        </Link>
      ))}
    </div>
  );
}

export default Categories