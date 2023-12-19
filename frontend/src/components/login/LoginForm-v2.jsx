import React from 'react';
import { useForm } from 'react-hook-form';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import {DevTool} from '@hookform/devtools';
import api from '../../api/axiosInterceptors';
const LoginForm = () => {
    const { register, handleSubmit,  formState: { errors },control } = useForm();
    const navigate = useNavigate();
    const location = useLocation();
    const { from } = location.state || { from: { pathname: "/" } };
    
    const handleLogin = async (data) => {
        try {
            const res = await api.post("/auth/authenticate", data);
            localStorage.setItem("accessToken", res.data.accessToken);
            localStorage.setItem("refreshToken", res.data.refreshToken);
            navigate(from, { replace: true });
            
        } catch (error) {

        }
        console.log(data);
    }   
    
    return (
        <div className="flex justify-center items-center mt-6 ">
      <form
        onSubmit={handleSubmit(handleLogin)}
        className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 w-[30%]"
      >
        <h2 className="text-2xl font-bold mb-4">Login</h2>
        <div className="mb-4">
          <input
            className={`shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline`}
            type="text"
            id='username'
            name='username'
            placeholder="Enter your username"
            autoComplete="off"
            {...register("username", {
                required: { value: true, message: "username is required" },
                  pattern: {
                      value: /^[a-zA-Z0-9]+$/,
                      message: "Invalid username",
                  },
  
              })}

            />
            <p className='text-red-600'>{errors.username?.message}</p>
        </div>
        <div className="mb-6">
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            type="password"
            name='password'
            placeholder="Enter your password"
            {...register("password", {
                required: { value: true, message: "password is required" },
                pattern: {
                  value: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,}$/,
                  message:
                    "Invalid password ",
                },
              })}
          />
            <p className='text-red-600'>{errors.password?.message}</p>
        </div>
        <div className="flex items-center justify-between">
          <Link
            to={"/register"}
            className="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800"
            // href="/signup"
          >
            Create an account
          </Link>
          <button
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            type="Submit"
          >
            Login
          </button>
        </div>
      </form>
      <DevTool control={control} />
    </div>
    );
};

export default LoginForm;
