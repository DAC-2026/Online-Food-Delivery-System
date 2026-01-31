import { useState} from "react";
import {useSelector, useDispatch} from 'react-redux'
import { useAddresses } from "../../hooks/useAddresses";
import AddressSection from "./AddressSelection";
import PaymentSection from "./PaymentSection";
import { buildOrderPayload } from "../../utils/orderUtils";
import {placeOrder} from '../../api/orderApi'
import { clearCart } from "../../store/cartSlice";
function Checkout() {
  const userId = 1;
  const [paymentMode, setPaymentMode] = useState(null);
  const cartItems = useSelector((state)=>state.cart.items);
  const dispatch = useDispatch();
  const handleCODorder = (order)=>{
    console.log("order placed", order)
    dispatch(clearCart())
    window.alert(`Order #${order.orderId} placed successfully (COD)`);
  }
  const {
    addresses,
    selectedAddressId,
    setSelectedAddressId,
    addAddress,
    loading,
  } = useAddresses(userId);

  if (loading) return <p>Loading...</p>;
  const handlePlaceOrder = async ()=>{
    const payload = buildOrderPayload({userId, deliveryAddressId:selectedAddressId , paymentMode, cartItems})
    // console.log(payload)
    try {
      const response = await placeOrder(payload);
      // console.log(response.data)
      if(response.data.paymentMode == 'COD') handleCODorder(response.data)
    } catch (error) {
      console.log(error.data)
    }
  }
  return (
    <div className="container mt-4">
      <AddressSection
        addresses={addresses}
        selectedAddressId={selectedAddressId}
        onSelect={setSelectedAddressId}
        onAddAddress={addAddress}
      />

      <hr />

      <PaymentSection
        paymentMode={paymentMode}
        setPaymentMode={setPaymentMode}
      />

      <button
        className={`btn mt-4 ${
          selectedAddressId && paymentMode
            ? "btn-primary"
            : "btn-secondary"
        }`}
        disabled={!selectedAddressId || !paymentMode}
        onClick= {handlePlaceOrder}
      >
        
        Continue
      </button>
    </div>
  );
}

export default Checkout;
