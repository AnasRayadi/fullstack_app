import axios from "axios";
import { getAccessToken, getRefreshToken } from "../util/auth";

const api = axios.create({
    baseURL: "http://localhost:8080/api/v1/",
    headers: { 'Content-Type': 'application/json' },
    withCredentials: true
});

api.interceptors.request.use(
  (config) => {
    const accessToken = getAccessToken();
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const originalRequest = error.config;
    if (error.response.status === 403 && !originalRequest.sent) {
      originalRequest.sent = true;
      const refreshToken = getRefreshToken();
      
      return axios.post("http://localhost:8080/api/v1/auth/refresh-token", {refreshToken}, 
      // {headers: { Authorization: `Bearer ${refreshToken}` },}
        )
        .then((res) => {
          if (res.status === 200) {
            // console.log(res.data.accessToken);
            localStorage.setItem("accessToken", res.data.accessToken);
            console.log("Access token refreshed!");
            return api(originalRequest);
          }
        });
    }
  },
  (error) => {
    console.log('refresh token error: ', error);
    // Do something with the response error
    return Promise.reject(error);
  }
);

export default api;
