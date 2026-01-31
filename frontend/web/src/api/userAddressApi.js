import axios from 'axios'
import {baseUrl} from '../utils/constants'
export const getUserAddress = (userId)=>{
    return axios.get(`${baseUrl}/users/${userId}/addresses`)
}

export const addUserAddress = (userId, addressObj)=>{
    return axios.post(`${baseUrl}/users/${userId}/addresses`,addressObj)
}