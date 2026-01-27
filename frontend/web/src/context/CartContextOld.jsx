// import {createContext, useState} from 'react'
// export const CartContext = createContext();


// export function CartProvider({children}){
//     const [cartItems, setCartItems] = useState({}); // state
//     const addItem = (itemId)=>{  // reducer
//         setCartItems(prev=>(
//             {...prev, [itemId] : (prev[itemId]||0)+1}
//         ))
//     }
//     const removeItem = (itemId)=>{ //reducer
//         setCartItems(prev=>{
//             const updated = {...prev}
//             if(updated[itemId] > 1) updated[itemId] -=1;
//             else delete updated[itemId];
//             return updated
//         })
//     }

//     const cartCount = Object.values(cartItems).reduce((sum, quantity)=>sum+quantity,0) //selector

//     return(
//         <CartContext.Provider value={{cartItems, cartCount, addItem, removeItem}}>
//             {children}
//         </CartContext.Provider>
//     );
// }