import axios from 'axios'
import { baseUrl } from '../utils/constants'

export const  getCategoriesByRestaurantId = (restaurantId) =>{
  return axios.get(baseUrl+`/${restaurantId}/categories`)
}

