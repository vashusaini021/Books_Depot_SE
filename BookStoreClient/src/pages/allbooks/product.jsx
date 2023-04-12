import React, { useContext } from "react";
import { ShopContext } from "../../context/shop-context";
import { Link } from "react-router-dom";
import cartservices from "../../services/cartservices";

export const Product = (props) => {
  const { id, title, price, thumbnailUrl } = props.data;
  const { addToCart, cartItems, user } = useContext(ShopContext);
  const cartItemCount = cartItems[id];

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

  return (
    <div className="product">
      <Link to={`/bookDetails/${id}`}>
        <img src={thumbnailUrl} alt={title} />
      </Link>
      <div className="description">
          <Link to={`/bookDetails/${id}`}>
            <b>{title}</b>
          </Link>
          <br/>
        ${price}
      </div>
      <button className="addToCartBttn" onClick={()=>addToCartAction(id)}> Add To Cart {cartItemCount > 0 && <> ({cartItemCount})</>}
      </button>
    </div>
  );
};



