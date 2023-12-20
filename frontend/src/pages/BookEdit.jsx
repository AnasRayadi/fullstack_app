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
      <div className="flex justify-center items-center mt-6">
        <h1 className="text-white">Edit Book Page!</h1>
      </div>
      <EditBookForm />
    </>
  );
};
export default BookEdit;
