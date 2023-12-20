import React from "react";
import { useNavigate, useNavigation, useParams } from "react-router-dom";
import api from "../../api/axiosInterceptors";
import { useForm } from "react-hook-form";

const EditBookForm = () => {
  const param = useParams();
  const bookId = param.bookId;

  const navigate = useNavigate();
  const navigation = useNavigation();
  const submiting = navigation.state === "submitting";
  const cancelHandler = () => {
    navigate("/books");
  };

  const { register, handleSubmit, formState: { errors } } = useForm(
    {

      defaultValues: async () => {
        const response = await api.get("/books/" + parseInt(bookId));
        const book = await response.data;

        return {
        title: book?.title,
        image: book?.image,
        author: book?.author,
        edition: book?.edition,
        description: book?.description
        }
      }
    }
  );
  const submitHandler = async (data) => {
    try {
        const res = await api.put("/books/" + parseInt(bookId), data);
        const bookData = await res.data;
        console.log(bookData);
    } catch (error) {
        console.error(error);
    }
    navigate("/books");
  }


  return (
    <form
      onSubmit={handleSubmit(submitHandler)}
      // id="bookForm"
      // method={method}
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
          {...register("title", 
          {required:{value:true, message:"Title is required"}}
          )}
          // defaultValue={book?.title}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          placeholder="Enter book title"
        />
        <p className="text-red-500 italic">{errors.title?.message}</p>
        
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
          {...register("image",
          {required:{value:true, message:"Image is required"}},
          {pattern:{value:/\.(jpeg|jpg|gif|png)$/i, message:"Image must be a url"}}
          )}
          // defaultValue={book?.image}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          placeholder="Enter image url"
        />
         
          <p className="text-red-500 italic">{errors.image?.message}</p>
        
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
          {...register("author", {required:{value:true, message:"Author is required"}})}
          // defaultValue={book?.author}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          placeholder="Enter author name"
          
        />
          <p className="text-red-500 italic">{errors.author?.message}</p>
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
          {...register("edition", {required:{value:true, message:"Edition is required"}})}
          // defaultValue={book?.edition}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          
        />
        
          <p className="text-red-500 italic">{errors.edition?.message}</p>
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
          {...register("description", {required:{value:true, message:"Description is required"}},
          {minLength:20 },
          {maxLength: 255}
          
          )}
          // defaultValue={book?.description}
          className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          placeholder="Enter description"
        />
        
          <p className="text-red-500 italic">{errors.description?.message}</p>
      
      </div>
      <div className="mb-4 flex justify-between">
        <button
          // disabled={submiting}
          className="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 mr-1 rounded focus:outline-none focus:shadow-outline"
          type="submit"
        >
          {/* {submiting ? "Submiting..." : "Save"} */}
          Save
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
    </form>
  );
};

export default EditBookForm;


// export async function action(params, request) {
//   try {
//     // const formData = await request.FormData();

//     const form = document.querySelector("#bookForm");
//     // Create a FormData object from the form
//     const formData = new FormData(form);

//     const book = {
//       title: formData.get("title"),
//       author: formData.get("author"),
//       image: formData.get("image"),
//       edition: formData.get("edition"),
//       description: formData.get("description"),
//     };
//     let requestMethod = "post";
//     let url = "/books/new";

//     if (request.method === "PATCH") {
//       const bookId = params.bookId;
//       requestMethod = "put";
//       url = "/books/" + parseInt(bookId);
//     }

//     const response = await api({ url: url, method: requestMethod, data: book });
//     if (response.status !== 200) {
//       throw json({ message: "something went wrong!" }, { status: 500 });
//     }
//     // console.log(book);
//     return redirect("/books");
//   } catch (error) {
//     console.error("Error processing form data:", error);
//   }
//   return null;
// }
