import React, { useContext } from "react";
import { ShopContext } from "../../context/shop-context";
import "./cart.css"
import cartservices from "../../services/cartservices";


export const CartItem = (props) => {
  const { id, title, price, thumbnailUrl } = props.data;
  const { cartItems, addToCart, removeFromCart, updateCartItemCount, user } = useContext(ShopContext);

  const addToCartAction = (id) => {
    if (user !== null) {
      console.log("user logged In")
      const userID = user.id

      const addToCartRequest = {
        "user": { "id": userID },
        "books": [{ "id": id }]
      }

      cartservices.addToCart(addToCartRequest)
        .then(response => {
          console.log(response.data)
        })
        .catch(error => {
          console.log(error.response.data.message);
        })
    }
    addToCart(id)

  }

  const removeFromCartAction = (id) => {
    if (user !== null) {
      console.log("user logged In")
      const userID = user.id

      const removeCartRequest = {
        "user": { "id": userID },
        "books": [{ "id": id }]
      }

      console.log(removeCartRequest)


      cartservices.removeFromCart(removeCartRequest)
        .then(response => {
          console.log(response.data)
        })
        .catch(error => {
          console.log(error.response.data.message);
        })
    }
    removeFromCart(id)

  }


  return (
    <div className="cartItem">
      <img src={thumbnailUrl} alt="" />
      <div className="desc">
        <p>
          <b>{title}</b>
        </p>
        <p> Price: ${price}</p>
        <div className="countHandler">
          <button onClick={() => removeFromCartAction(id)}> - </button>
          <input
            value={cartItems[id]}
            onChange={(e) => updateCartItemCount(Number(e.target.value), id)}
          />
          <button onClick={() => addToCartAction(id)}> + </button>
        </div>
      </div>
    </div>
  );
};
