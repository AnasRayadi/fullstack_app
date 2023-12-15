import React from "react";
import { Link, NavLink, useLocation, useNavigate } from "react-router-dom";
import { getAccessToken } from "../../util/auth";

const MainNavigation = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { from } = location.state || { from: { pathname: "/" } };

  const token = getAccessToken();
  
  const logoutHandler = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    
    navigate(from, { replace: true });
  };

  return (
    <nav className="bg-gray-800">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          <div className="flex items-center">
            <div className="flex-shrink-0">
              <Link to={"/"} className="text-white font-bold text-xl">
                My Website
              </Link>
            </div>
            <div className="hidden md:block">
              <div className="ml-10 flex items-baseline space-x-4">
                <NavLink
                  to={"/"}
                  className={({ isActive }) =>
                    isActive
                      ? "bg-gray-900 text-white px-3 py-2 rounded-md text-sm font-medium"
                      : "text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium"
                  }
                >
                  Home
                </NavLink>
                <NavLink
                  to={"books"}
                  className={({ isActive }) =>
                    isActive
                      ? "bg-gray-900 text-white px-3 py-2 rounded-md text-sm font-medium"
                      : "text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium"
                  }
                >
                  Books
                </NavLink>
                {!token && <NavLink
                  to={"login"}
                  // className="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium"
                  className={({ isActive }) =>
                    isActive
                      ? "bg-gray-900 text-white px-3 py-2 rounded-md text-sm font-medium"
                      : "text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium"
                  }
                >
                  Login
                </NavLink>}
                {!token && <NavLink
                  to={"register"}
                  className={({ isActive }) =>
                    isActive
                      ? "bg-gray-900 text-white px-3 py-2 rounded-md text-sm font-medium"
                      : "text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium"
                  }
                >
                  Register
                </NavLink>}
                {token 
                &&
                <button
                  onClick={logoutHandler}
                  className="text-gray-300 bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium"
                >
                  Logout
                </button>
                }
              </div>
            </div>
          </div>
          
        </div>
      </div>
      
    </nav>
  );
};

export default MainNavigation;
