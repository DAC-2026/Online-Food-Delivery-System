import axios from 'axios';
import { baseUrl } from '../utils/constants';

const CLOUDINARY_URL = `https://api.cloudinary.com/v1_1/${import.meta.env.VITE_CLOUDINARY_CLOUD_NAME}/image/upload`;
const UPLOAD_PRESET = import.meta.env.VITE_CLOUDINARY_UPLOAD_PRESET;

export const uploadImageToCloudinary = async (file) => {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("upload_preset", UPLOAD_PRESET);
    formData.append("folder", "OnlineFoodOrderingSystem");

    const response = await axios.post(CLOUDINARY_URL, formData);
    return response.data.secure_url;
};

export const createRestaurant = (data) => {
    return axios.post(`${baseUrl}/management/restaurants`, data);
};

export const createCategory = (restaurantId, data) => {
    return axios.post(`${baseUrl}/management/${restaurantId}/categories`, data);
};

export const createMenuItem = (categoryId, data) => {
    return axios.post(`${baseUrl}/management/categories/${categoryId}/items`, data);
};
