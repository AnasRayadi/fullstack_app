import React from "react";
import NewBookForm from "../components/book/NewBookForm";

const NewBook = () => {
  return (
    <>
      <div className="flex justify-center items-center mt-6">
        <h1 className="text-white">New book page !</h1>
      </div>
      <NewBookForm />
    </>
  );
};
export default NewBook;
