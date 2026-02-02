import axios from 'axios'
import { baseUrl } from '../utils/constants'

export const createRazorpayOrder = (orderPayload) => {
    return axios.post(`${baseUrl}/payment/create-order`, orderPayload)
}

export const verifyPayment = (paymentData) => {
    return axios.post(`${baseUrl}/payment/verify`, paymentData)
}
