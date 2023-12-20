import React from "react";
import { useForm } from "react-hook-form";
import api from "../../api/axiosInterceptors";

const EditionDateFilter = ({onFilter}) => {
    const { register, handleSubmit, formState: { errors } } = useForm()
    const submitHandler = async (dates) => {
        try {
            const data = {
                startDate: `${dates.startDate}` ,
                endDate: `${dates.endDate}`
            }
            // const startDate = dates.startDate ;
            // const endDate = dates.endDate ;
            // console.log(startDate, endDate);
            const res = await api.post("/books/filter", data);
            const booksData = await res.data;
            console.log(booksData);
            onFilter(booksData);
        } catch (error) {
            console.error(error);
        }
    }


  return (
    <div className="flex justify-center">
      <form onSubmit={handleSubmit(submitHandler)} className=" w-[45%] mb-4 border border-blue-600 p-2 rounded-lg">
        <div className="flex rounded-lg">
          <div className="mr-2">
            <label
              htmlFor="startDate"
              className="text-gray-400 text-sm font-bold mb-2 mr-2"
            >
              Start Date
            </label>
            <input
              type="date"
              id="startDate"
              name="startDate"
              // defaultValue={book?.startDate}
              className="shadow appearance-none border rounded w-36 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                {...register("startDate", { required: true })}
            />
          </div>
          <div>
            <label
              htmlFor="endDate"
              className="text-gray-400 text-sm font-bold mb-2 mr-2"
            >
              End Date
            </label>
            <input
              type="date"
              id="endDate"
              name="endDate"
              // defaultValue={book?.endDate}
              className="shadow appearance-none border rounded w-36 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                {...register("endDate", { required: true })}
            />
          </div>
          <div>
            <button
              type="submit"
              className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded ml-2"
            >
              Filter
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default EditionDateFilter;
