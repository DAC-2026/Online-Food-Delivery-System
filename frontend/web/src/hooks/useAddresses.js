import { useEffect, useState } from "react";
import {
  getUserAddress,
  addUserAddress,
} from "../api/userAddressApi";

export function useAddresses(userId) {
  const [addresses, setAddresses] = useState([]);
  const [selectedAddressId, setSelectedAddressId] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getUserAddress(userId)
      .then((res) => setAddresses(res.data))
      .catch((err) => console.error("Fetch address error", err))
      .finally(() => setLoading(false));
  }, [userId]);

  const addAddress = async (newAddress) => {
    const response = await addUserAddress(userId, newAddress);
    const saved = response.data;

    setAddresses((prev) => [...prev, saved]);
    setSelectedAddressId(saved.addressId);
  };

  return {
    addresses,
    selectedAddressId,
    setSelectedAddressId,
    addAddress,
    loading,
  };
}
