import axios from 'axios'
import { baseUrl } from '../utils/constants'

export const getItemByRestaurantId = (restaurantId)=>{
    return axios.get(baseUrl+`/restaurant/${restaurantId}/items`)
}
export const getItemByCategoryId = (categoryId)=>{
    return axios.get(baseUrl+`/categories/${categoryId}/items`)
}