import React from "react";
import { Form, json, redirect, useNavigate, useNavigation } from "react-router-dom";
import api from "../../api/axiosInterceptors";

const BookForm = ({ method, book }) => {
  const navigate = useNavigate();
  const navigation = useNavigation();
  const submiting = navigation.state === "submitting";
  const cancelHandler = () => {
    navigate("..");
  };

  return (
    <Form
      id="bookForm"
      method={method}
      className="max-w-lg mx-auto p-4 rounded-lg bg-slate-700 shadow-sm"
    >
      <div className="mb-4">
        <label
          htmlFor="title"
          className="block text-gray-400 text-sm font-bold mb-2"
        >
          Title
        </label>

        <input
          type="text"
          id="title"
          name="title"
          defaultValue={book?.title}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          placeholder="Enter book title"
          required
        />
      </div>
      <div className="mb-4">
        <label
          htmlFor="image"
          className="block text-gray-400 text-sm font-bold mb-2"
        >
          Image
        </label>
        <input
          type="text"
          id="image"
          name="image"
          defaultValue={book?.image}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          placeholder="Enter image url"
          required
        />
      </div>
      <div className="mb-4">
        <label
          htmlFor="author"
          className="block text-gray-400 text-sm font-bold mb-2"
        >
          Author
        </label>
        <input
          type="text"
          id="author"
          name="author"
          defaultValue={book?.author}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          placeholder="Enter author name"
          required
        />
      </div>
      <div className="mb-4">
        <label
          htmlFor="publisheDate"
          className="block text-gray-400 text-sm font-bold mb-2"
        >
          Publishe Date
        </label>
        <input
          type="date"
          id="edition"
          name="edition"
          defaultValue={book?.edition}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          required
        />
      </div>
      <div className="mb-4">
        <label
          htmlFor="description"
          className="block text-gray-400 text-sm font-bold mb-2"
        >
          Description
        </label>
        <textarea
          type="text"
          id="description"
          name="description"
          defaultValue={book?.description}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          placeholder="Enter description"
          required
        />
      </div>
      <div className="mb-4 flex justify-between">
        <button
          disabled={submiting}
          className="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 mr-1 rounded focus:outline-none focus:shadow-outline"
        >
          {submiting ? "Submiting..." : "Save"}
        </button>

        <button
          onClick={cancelHandler}
          className="w-full bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 ml-1 rounded focus:outline-none focus:shadow-outline"
        >
          Cancel
        </button>
      </div>
      {/* <button>Cancel</button>
      <button
        type="submit"
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
        disabled={submiting}
        >
          {submiting ? 'Submiting...':'Save'}
      </button> */}
    </Form>
  );
};

export default BookForm;

export async function action(params, request) {
  try {
    // const formData = await request.FormData();

    const form = document.querySelector("#bookForm");
    // Create a FormData object from the form
    const formData = new FormData(form);

    const book = {
      title: formData.get("title"),
      author: formData.get("author"),
      image: formData.get("image"),
      edition: formData.get("edition"),
      description: formData.get("description"),
    };
    let requestMethod = "post";
    let url = "/books/new";

    if (request.method === "PATCH") {
      const bookId = params.bookId;
      requestMethod = "put";
      url = "/books/" + parseInt(bookId);
    }

    const response = await api({ url: url, method: requestMethod, data: book });
    if (response.status !== 200) {
      throw json({ message: "something went wrong!" }, { status: 500 });
    }
    // console.log(book);
    return redirect("/books");
  } catch (error) {
    console.error("Error processing form data:", error);
  }
  return null;
}
