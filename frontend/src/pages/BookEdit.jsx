import React from "react";
import { useRouteLoaderData } from "react-router";
import BookForm from "../components/book/BookForm";
const BookEdit = () => {
  const data = useRouteLoaderData("book-details");
  const book = data.bookData;
  console.log(book);
  return (
    <>
      <h1 className="text-white">Book Edit Page</h1>
      <BookForm book={book} method='PATCH'/>
    </>
  );
};
export default BookEdit;
