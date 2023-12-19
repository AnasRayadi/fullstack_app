import React, { useEffect, useRef, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import axiosInstanse from "../../api/axios";
// import {useForm} from 'react-hook-form'
const LoginForm = () => {

  // const {register, handleSubmit, formState: { errors }} = useForm();
  
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errMsg, setErrMsg] = useState("");

  const userRef = useRef();
  const errRef = useRef();

  const navigate = useNavigate();
  const location = useLocation();
  const { from } = location.state || { from: { pathname: "/" } };

  useEffect(() => {
    userRef.current.focus();
  }, []);

  useEffect(() => {
    setErrMsg("");
  }, [username, password]);

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axiosInstanse.post("auth/authenticate", {
        username,
        password,
      });
      console.log(response.data);

      const accessToken = response.data.accessToken;
      const refreshToken = response.data.refreshToken;

      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);

      setUsername("");
      setPassword("");
      navigate(from, { replace: true });
    } catch (err) {
      if (!err?.response) {
        setErrMsg("No Server Response");
      } else if (err.response?.status === 400) {
        setErrMsg("Missing Username or Password");
      } else if (err.response?.status === 401) {
        setErrMsg("Unauthorized");
      } else {
        setErrMsg("Login Failed");
      }
      errRef.current.focus();
    }
  };

  // const extractRoles = (token) => {
  //   let payload = token.split(".")[1];
  //   // decode the payload using window.atob
  //   let decoded = window.atob(payload);
  //   // parse the decoded string as a JSON object
  //   let parsed = JSON.parse(decoded);
  //   // return the role property of the object
  //   return parsed.roles;
  // };

  return (
    <div className="flex justify-center items-center mt-6 ">
      <form
        onSubmit={handleLogin}
        className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 w-[30%]"
      >
        <h2 className="text-2xl font-bold mb-4">Login</h2>
        <p
          ref={errRef}
          className={errMsg ? "errmsg" : "offscreen"}
          aria-live="assertive"
        >
          {errMsg}
        </p>
        <div className="mb-4">
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="username"
            type="text"
            placeholder="Enter your username"
            autoComplete="off"
            ref={userRef}
            value={username}
            onChange={handleUsernameChange}
          />
        </div>
        <div className="mb-6">
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="password"
            type="password"
            placeholder="Enter your password"
            value={password}
            onChange={handlePasswordChange}
          />
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
    </div>
  );
};

export default LoginForm;
