import {createSlice} from '@reduxjs/toolkit'

const cartSlice = createSlice({
    name : "cart",
    initialState : {
        items : {}
    },
    reducers :{
        addItem : (state, action)=>{  // action
        const item = action.payload;
        const existingItem = state.items[item.id]
        if(existingItem){
            existingItem.quantity+=1
        }
        else{
            state.items[item.id] = {
                id:item.id,
                name:item.name,
                price:item.price,
                imageUrl : item.imageUrl,
                isVeg : item.isVeg,
                quantity : 1
            }
        }
    },
    removeItem: (state, action) => {
        const itemId = action.payload;
        const existingItem = state.items[itemId];

        if (!existingItem) return;

        if (existingItem.quantity > 1) {
            existingItem.quantity -= 1;
        } else {
            delete state.items[itemId];
        }
    },
    clearCart(state){
        state.items = {};
    }
    }
})
export const selectCartCount = (state)=>Object.values(state.cart.items).reduce((sum, item)=>sum+item.quantity,0)
export const selectCartTotal = (state)=>Object.values(state.cart.items).reduce((sum, item)=>sum+item.quantity*item.price,0)
export const {addItem, removeItem, clearCart} = cartSlice.actions;
export default cartSlice.reducer;