import React, { createContext, useState, useEffect } from "react";
import bookservices from "../services/bookservices";
import { baseURLApp } from "../http-common";

export const ShopContext = createContext(null);
export const ShopContextProvider = (props) => {

  const [books, setBooks] = useState([]);
  const [user, setUser] = useState(null);
  const [originalBooks, setOriginalBooks] = useState([]);
  const [searchString, setSearchString] = useState("");
  const [featuredBooks, setFeaturedBooks] = useState([]);
  const [bestSellers, setbestSellers] = useState([]);

  useEffect(() => {
    fetch(`${baseURLApp}/books/allbooks`)
      .then((response) => response.json())
      .then((data) => {
        setOriginalBooks(data)
        setBooks(data);
      })
      .catch((error) => console.log(error));
  }, []);

  useEffect(() => {
    if (searchString !== "") {

      //LOCAL SEARCH
      // const results = originalBooks.filter(book =>
      //   book.title.toLowerCase().includes(searchString.toLowerCase())
      // );

      //API SEARCH
      console.log(searchString)

      bookservices.searchBook(searchString)
        .then(response => {
          console.log(response.data)
          setBooks(response.data)
        }
        )
        .catch(error => {
          console.log(error.response);
        })

      // setBooks(results)
    } else {
      setBooks(originalBooks)
    }

  }, [searchString, originalBooks]);

  useEffect(() => {
    fetch(`${baseURLApp}/books/featured`)
      .then((response) => response.json())
      .then((data) => {
        setFeaturedBooks(data);
      })
      .catch((error) => console.log(error));
  }, []);

  useEffect(() => {
    fetch(`${baseURLApp}/books/bestseller`)
      .then((response) => response.json())
      .then((data) => {
        setbestSellers(data);
      })
      .catch((error) => console.log(error));
  }, []);


  const [cartItems, setCartItems] = useState({});

  const getTotalCartAmount = () => {
    let totalAmount = 0;
    for (const item in cartItems) {
      let itemInfo = books.find((b) => {
        // eslint-disable-next-line
        return b.id == item;
      });
      if (itemInfo) {
        totalAmount += cartItems[item] * itemInfo.price;
      }
    }
    return totalAmount;
  };

  const addToCart = (itemId) => {
    setCartItems((prev) => {
      if (prev[itemId]) {
        return { ...prev, [itemId]: prev[itemId] + 1 };
      } else {
        return { ...prev, [itemId]: 1 };
      }
    });
  };

  const removeFromCart = (itemId) => {
    setCartItems((prev) => {
      if (prev[itemId] > 1) {
        return { ...prev, [itemId]: prev[itemId] - 1 };
      } else {
        const { [itemId]: removedItem, ...updatedItems } = prev;
        return updatedItems;
      }
    });
  };


  const updateCartItemCount = (newAmount, itemId) => {
    setCartItems((prev) => ({ ...prev, [itemId]: newAmount }));
  };

  const checkout = () => {
    setCartItems({});
  };

  const bookDetails = (itemId) => {

    console.log(itemId)
  };


  const contextValue = {
    setSearchString,
    setUser,
    user,
    searchString,
    cartItems,
    books,
    featuredBooks,
    bestSellers,
    setCartItems,
    addToCart,
    bookDetails,
    updateCartItemCount,
    removeFromCart,
    getTotalCartAmount,
    checkout,
  };

  return (
    <ShopContext.Provider value={contextValue}>
      {props.children}
    </ShopContext.Provider>
  );
};
