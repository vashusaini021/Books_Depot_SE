import React, { useContext, useState } from "react";
import { Product } from "./product";
import { ShopContext } from "../../context/shop-context";
import Label from "./label";

import "./shop.css";

function Shop() {
  const { books, featuredBooks, bestSellers } = useContext(ShopContext);
  const [selectedValue, setSelectedValue] = useState("all-books");

  const renderProducts = (selectedValue) => {
    const searchBar = document.querySelector("#search-form")
    if (searchBar !== null) {
      searchBar.style.display = "none";
    }

    switch(selectedValue) {
      case "featured":
        return (
          <>
            <Label title="Featured Books" />
            <div className="products">
              {featuredBooks.map((product) => (
                <Product key={product.id} data={product} />
              ))} 
            </div>
          </>
        );
      case "best-sellers":
        return (
          <>
            <Label title="Best Sellers" />
            <div className="products">
              {bestSellers.map((product) => (
                <Product key={product.id} data={product} />
              ))}
            </div>
          </>
        );
      case "all-books":
        if (searchBar !== null) {
          searchBar.style.display = "block";
        }
        return (
          <>
            <Label title="All Books" />
            <div className="products">
              {books.map((product) => (
                <Product key={product.id} data={product} />
              ))}
            </div>
          </>
        );
      default:
        return null;
    }
  }

  return (
    <div className="option">
      <select className="bookOptions" onChange={(e) => setSelectedValue(e.target.value)}> 
        <option value="all-books">All Books</option>
        <option value="featured">Featured Books</option>
        <option value="best-sellers">Best Sellers</option>
      </select>
      { renderProducts(selectedValue)}
    </div>
  );
}

export default Shop;
