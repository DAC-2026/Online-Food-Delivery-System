import {BrowserRouter, Route, Routes} from 'react-router-dom'
import Home from './pages/user/Home'
import Orders from './pages/user/Orders'
import Restaurant from './pages/user/Restaurant'
import Navbar from './components/NavBar'
import RestaurantDetails from './pages/user/RestaurantDetails'
import MenuItems from './components/MenuItemCard'
// import {CartProvider} from './context/CartContext'
import {Provider} from 'react-redux'
import {store} from './store/store'
import Cart from './pages/user/Cart'
function App() {
  
  return (
    <>
      <BrowserRouter>
        <Provider store={store}>
            <Navbar/>
            <Routes>
              <Route path = "/" element =  {<Home/>}/>
              <Route path = "/orders" element =  {<Orders/>}/>
              <Route path = "/restaurant" element =  {<Restaurant/>}/>
              <Route path = "/restaurants/:restaurantId" element =  {<RestaurantDetails/>}/>
              <Route path="/menuItem/:categoryId" element={<MenuItems />} />
              <Route path = "/cart" element = {<Cart/>}/>
            </Routes>
        </Provider>
      </BrowserRouter>
    </>
  )
}

export default App
