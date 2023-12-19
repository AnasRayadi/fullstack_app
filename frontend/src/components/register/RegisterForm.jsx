import { DevTool } from "@hookform/devtools";
import React from "react";
import { useForm } from "react-hook-form";
import api from "../../api/axiosInterceptors";
import { redirect, useLocation, useNavigate } from "react-router";

const RegisterForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    control,
  } = useForm();

  const navigate = useNavigate();
    const location = useLocation();
    const { from } = location.state || { from: { pathname: "/login" } };
  const handleRegister = async(data) => {
    // TODO: Implement registration logic
    try {
        const user = {
            name: data.fullName,
            email: data.email,
            username: data.username,
            password: data.password,
        };
        const res = await api.post("/auth/register", user);
        console.log(res.data);
        navigate(from, { replace: true });
        // localStorage.setItem("accessToken", res.data.accessToken);
        // localStorage.setItem("refreshToken", res.data.refreshToken);

       return redirect("/login");
    } catch (error) {
        console.log(error);
    }
    console.log(data);
  };

  return (
    <div className="flex justify-center items-center mt-6">
      <form
        onSubmit={handleSubmit(handleRegister)}
        className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 w-[30%]"
      >
        <h2 className="text-2xl font-bold mb-4">Register</h2>

        <div className="mb-4">
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="fullName"
            type="text"
            placeholder="Enter your full name"
            autoComplete="off"
            {...register("fullName", {
                required: { value: true, message: "Full name is required" },
            })}
          />
          <p className="text-red-600">{errors.fullName?.message}</p>
        </div>
        <div className="mb-4">
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="email"
            type="email"
            placeholder="Enter your email"
            autoComplete="off"
            {...register(
              "email",
              { required: { value: true, message: "email is required" } },
              {
                pattern: {
                  value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                  message: "Please enter a valid email address",
                },
              }
            )}
          />
            <p className="text-red-600">{errors.email?.message}</p>
        </div>
        <div className="mb-4">
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="username"
            type="text"
            placeholder="Enter your username"
            autoComplete="off"
            {...register("username", {
              required: { value: true, message: "username is required" },
                pattern: {
                    value: /^[a-zA-Z0-9]+$/,
                    message: "Username must contain only letters and numbers",
                },

            })}
          />
            <p className="text-red-600">{errors.username?.message}</p>
        </div>
        <div className="mb-6">
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="password"
            type="password"
            placeholder="Enter your password"
            {...register("password", {
              required: { value: true, message: "password is required" },
              pattern: {
                value: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,}$/,
                message:
                  "Password must contain at least 6 characters, including UPPER/lowercase and numbers",
              },
            })}
          />
            <p className="text-red-600">{errors.password?.message}</p>
        </div>
        <div className="flex items-center justify-between">
          <button
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            type="submit"
          >
            Register
          </button>
        </div>
      </form>
      <DevTool control={control} />
    </div>
  );
};

export default RegisterForm;
