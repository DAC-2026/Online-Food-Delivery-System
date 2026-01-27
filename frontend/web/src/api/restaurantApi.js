import axios from 'axios'
import {baseUrl} from '../utils/constants'
export const getAllRestaurant = ()=>{
    return axios.get(baseUrl+"/restaurants");
}

export const getRestaurantById = (id)=>{
    return axios.get(baseUrl+`/restaurants/${id}`)
}