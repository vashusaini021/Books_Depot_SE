import React, { useEffect, useState } from "react";
import { useParams } from 'react-router-dom';
import "./bookDetails.css"
import { baseURLApp } from "../../http-common";

function BookDetails() {
        const { id } = useParams();

    const [book, setBook] = useState({});
      useEffect(() => {
        fetch(`${baseURLApp}/books/details/`+id)
            .then(response => response.json() )
            .then(data => {
              console.log(data)
              setBook(data)
            })
            .catch(error => console.log(error));
    }, [id]);
    
  return (
    <div className="details">
  <div className="bookimg">
    <img src={book.thumbnailUrl} alt={book.title} />
  </div>
  <div className="description">
    <h2> <b>{book.title}</b> </h2>
    <p> <b>Published Date:</b> {new Date(book.publishedDate).toLocaleDateString()} &emsp;| &emsp;
        <b>ISBN:</b> {book.isbn} &emsp; | &emsp;
        <b>Page Count:</b> {book.pageCount}</p>
    <p> <b>Price: ${book.price}</b> </p>
    <p> <b>Short Description:</b> {book.shortDescription}</p>
    <p> <b>Long Description:</b> {book.longDescription}</p>
    <p> <b>Authors:</b> {book.authors} &emsp; | &emsp; 
        <b>Categories:</b> {book.categories} &emsp;|&emsp;
        <b>Status:</b> {book.status}</p>
  </div>
  </div>
  );
}

export default BookDetails;
