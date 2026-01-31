export const buildOrderPayload = ({
    userId, // temporary, will come from auth later
    deliveryAddressId,
    paymentMode,
    cartItems,
})=>{
    return {
        userId,
        deliveryAddressId,
        paymentMode,
        items : Object.values(cartItems).map(item=>({
            menuItemId : item.id,
            quantity : item.quantity,
        }))
    }
}