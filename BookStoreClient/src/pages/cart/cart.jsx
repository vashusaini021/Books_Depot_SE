import React, { useContext, useEffect } from "react";
import { ShopContext } from "../../context/shop-context";
import { CartItem } from "./cart-item";
import { useNavigate } from "react-router-dom";

import "./cart.css";
import cartservices from "../../services/cartservices";


function Cart() {

  const { cartItems, getTotalCartAmount, books, user, setCartItems } = useContext(ShopContext);
  const totalAmount = getTotalCartAmount();
  const navigate = useNavigate();


  useEffect(() => {
    if (user !== null) {
      console.log("user logged In")
      const userID = user.id

      cartservices.getCartItems(userID)
        .then(response => {
          var cartResponsebooks = response.data
          let carItems = {}
          for (const index in cartResponsebooks) {
            console.log(index)
            const item = cartResponsebooks[index];
            carItems[item.id] = item.quantity
          }
          setCartItems(carItems)
        }
        )
        .catch(error => {
          console.log(error.response);
        })
    }
  }, [user, setCartItems]);



  return (
    <div className="cart">
      <div>
        <h2><b>Your Cart Items</b></h2>
      </div>
      <div className="cart">
        {books.map((book) => {
          if (cartItems[book.id]) {
            return <CartItem key={book.id} data={book} />;
          }
          return null;
        })}
      </div>
      {totalAmount > 0 ? (
        <div className="checkout">
          <p> Subtotal: ${totalAmount} </p>
          <button onClick={() => navigate("/")}> Continue Shopping </button>
          <button onClick={() => navigate("/")}> Checkout </button>
        </div>
      ) : (
        <h5 className="empty"> Your Shopping Cart is Empty</h5>
      )}
    </div>

  );

}


export default Cart;