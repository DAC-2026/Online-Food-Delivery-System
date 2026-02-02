import { useState } from "react";
import { useSelector, useDispatch } from 'react-redux'
import { useAddresses } from "../../hooks/useAddresses";
import AddressSection from "./AddressSelection";
import PaymentSection from "./PaymentSection";
import { buildOrderPayload } from "../../utils/orderUtils";
import { placeOrder } from '../../api/orderApi'
import { clearCart } from "../../store/cartSlice";
import { createRazorpayOrder } from "../../api/paymentApi";
import { loadRazorpay } from "../../utils/loadRazorPay";

function Checkout() {
  const userId = 1;
  const [paymentMode, setPaymentMode] = useState(null);
  const cartItems = useSelector((state) => state.cart.items);
  const dispatch = useDispatch();

  const handleCODorder = (order) => {
    console.log("order placed", order)
    dispatch(clearCart())
    window.alert(`Order #${order.orderId} placed successfully (COD)`);
  }

  const handleOnlineOrder = async (orderPayload) => {
    const loaded = await loadRazorpay();

    if (!loaded) {
      alert("Razorpay failed to load");
      return;
    }

    try {
      // 1. Initiate Payment (Get Razorpay Order ID without saving to DB)
      const response = await createRazorpayOrder(orderPayload);
      const razorpayOrderId = response.data;

      const options = {
        key: "rzp_test_SBAfoJuuklOSC7",
        currency: "INR",
        name: "Foodies",
        description: "Order Payment",
        order_id: razorpayOrderId,
        prefill: {
          name: "Test User",
          email: "test.user@example.com",
          contact: "9999999999"
        },
        handler: async function (response) {
          try {
            // 2. on Success, add payment details to payload and Place Order
            const finalPayload = {
              ...orderPayload,
              razorpayOrderId: response.razorpay_order_id,
              razorpayPaymentId: response.razorpay_payment_id,
              razorpaySignature: response.razorpay_signature
            };

            const orderResponse = await placeOrder(finalPayload);

            dispatch(clearCart());
            window.alert(`Payment Successful! Order #${orderResponse.data.orderId} placed.`);
          } catch (error) {
            console.error("Order placement failed after payment", error);
            alert("Payment successful but Order placement failed. Please contact support.");
          }
        },
        theme: {
          color: "#dc3545",
        },
      };

      const razorpay = new window.Razorpay(options);
      razorpay.open();
    } catch (error) {
      console.error("Error creating Razorpay order", error);
      alert("Failed to initiate payment. Please try again.");
    }
  };

  const handlePlaceOrder = async () => {
    const payload = buildOrderPayload({ userId, deliveryAddressId: selectedAddressId, paymentMode, cartItems })

    if (paymentMode === 'COD') {
      try {
        const response = await placeOrder(payload);
        handleCODorder(response.data)
      } catch (error) {
        console.log(error)
        alert("Failed to place COD order. Please try again.");
      }
    } else {
      // Online Payment Flow
      handleOnlineOrder(payload)
    }
  }

  const {
    addresses,
    selectedAddressId,
    setSelectedAddressId,
    addAddress,
    loading,
  } = useAddresses(userId);

  if (loading) return <p>Loading...</p>;

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
        className={`btn mt-4 ${selectedAddressId && paymentMode
          ? "btn-primary"
          : "btn-secondary"
          }`}
        disabled={!selectedAddressId || !paymentMode}
        onClick={handlePlaceOrder}
      >

        Continue
      </button>
    </div>
  );
}

export default Checkout;
