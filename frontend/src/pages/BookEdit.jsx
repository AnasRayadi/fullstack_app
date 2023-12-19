import React from "react";
import { useRouteLoaderData } from "react-router";
import BookForm from "../components/book/NewBookForm";
import EditBookForm from "../components/book/EditBookForm";
const BookEdit = () => {
  // const data = useRouteLoaderData("book-details");
  // const book = data.bookData;
  // console.log(book);
  return (
    <>
      <h1 className="text-white">Book Edit Page</h1>
      <EditBookForm/>
    </>
  );
};
export default BookEdit;
