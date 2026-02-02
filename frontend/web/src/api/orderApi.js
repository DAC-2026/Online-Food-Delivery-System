import axios from 'axios'
import { baseUrl } from '../utils/constants'

export const placeOrder = (orderPayload) => {
    return axios.post(baseUrl + "/orders", orderPayload)
}

export const getUserOrders = (userId) => {
    return axios.get(`${baseUrl}/users/${userId}/orders`)
}