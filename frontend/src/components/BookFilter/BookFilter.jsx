import React, { useEffect, useState } from "react";
import { Controller, useForm } from "react-hook-form";
import api from "../../api/axiosInterceptors";

const BookFilter = ({ onFilter }) => {
  const [categories, setCategories] = useState([]);
  const { register, control, handleSubmit } = useForm({
    defaultValues: {
      category: null,
      startDate: null,
      endDate: null,
    },
  });

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const res = await api.get("/categories");
        const categoriesData = await res.data;
        setCategories(categoriesData);
      } catch (error) {
        console.error(error);
      }
    };
    fetchCategories();
  }, []);

  const filterHandler = async (filterParams) => {
    // console.log(filterParams);
    
    const category = filterParams.category == 'All' ? filterParams.category == null :  JSON.parse(filterParams.category) ;
    
    const data = {
      startDate: filterParams.startDate,
      endDate: filterParams.endDate,
      categoryId: category ? category.categoryId : null ,
    };
    // console.log(data);
    
    try {
      const res = await api.post("/books/filter", data);
      const booksData = await res.data;
      // console.log(res);
      onFilter(booksData);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      <div className="w-full flex items-center justify-center">
        <form
          onSubmit={handleSubmit(filterHandler)}
          className="flex flex-row bg-slate-300 w-[70%] p-1 rounded-lg"
        >
          <div className="flex flex-col items-start bg-slate-300 w-[25%] p-2">
            <label className="text-slate-600 text-lg mb-1 " htmlFor="startDate">
              Start Date
            </label>
            <input
              type="date"
              id="startDate"
              {...register("startDate")}
              className="w-full h-11 shadow-md rounded p-1"
            />
          </div>
          <div className="flex flex-col items-start bg-slate-300 w-[25%] p-2">
            <label className="text-slate-600 text-lg mb-1 " htmlFor="endDate">
              End Date
            </label>
            <input
              type="date"
              id="endDate"
              {...register("endDate")}
              className="w-full h-11 shadow-md rounded p-1"
            />
          </div>
          <div className="flex flex-col items-start bg-slate-300 w-[25%] p-2">
            <label className="text-slate-600 text-lg mb-1 " htmlFor="category">
              Category
            </label>
            {/* <select
              id="category"
              {...register("category")}
              className="w-full h-11 shadow-md rounded p-1"
            >
              <option value={null}>All</option>
              {categories.map((category) => (
                <option key={category.id} value={JSON.stringify(category)}>
                  {category.name}
                </option>
              ))}
            </select> */}
            <Controller
              name="category"
              control={control}
              render={({ field }) => (
                <select
                  {...field}
                  id="category"
                  className="w-full h-11 shadow-md rounded p-1"
                >
                  <option key="all" value={null}>
                    All
                  </option>
                  {categories.map((category) => (
                    <option
                      key={category.categoryId}
                      value={JSON.stringify(category)}
                    >
                      {category.name}
                    </option>
                  ))}
                </select>
              )}
            />
          </div>

          <div className="flex items-center bg-slate-300  w-[25%]">
            <button
              type="submit"
              className="bg-blue-500 hover:bg-blue-600 text-white font-bold w-[50%] mt-8 py-2 px-4 rounded-md"
            >
              Filter
            </button>
          </div>
        </form>
      </div>
    </>
  );
};

export default BookFilter;
