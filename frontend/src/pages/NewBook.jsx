import React from "react";
import BookForm from "../components/book/BookForm";

const NewBook = () => {
  return (
    <>
      <h1 className="text-white">new book page !</h1>
      <BookForm method='post'/>
    </>
  );
};
export default NewBook;
