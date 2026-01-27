export const buildOrderPayload = ({
    cartItems,
    restaurantId,
    paymentMode,
    deliveryAddressId,
    userId = 1, // temporary, will come from auth later
})=>{
    return {
        userId,
        restaurantId,
        paymentMode,
        deliveryAddressId,
        items : Object.values(cartItems).map(item=>({
            menuItemId : item.id,
            quantity : item.quantity,
        }))
    }
}