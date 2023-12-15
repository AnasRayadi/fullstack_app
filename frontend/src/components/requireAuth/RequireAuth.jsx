import { Navigate, Outlet, useLocation } from "react-router-dom";
import { extractRoles, getAccessToken } from "../../util/auth";

const RequireAuth = ({ allowedRoles }) => {
  const location = useLocation();
  const accessToken = getAccessToken();

//   let roles = null
//   if(accessToken){
//     roles = extractRoles(accessToken);
//   }

  const roles = extractRoles(accessToken);
  
  return (
    <>
      {/* {allowedRoles.includes(roles) ? ( */}
      {roles?.find((role) => allowedRoles?.includes(role)) ? (
        <Outlet />
      ) : accessToken ? (
        <Navigate to={"/unauthorized"} state={{ from: location }} replace />
      ) : (
        <Navigate to={"/login"} state={{ from: location }} replace />
      )}
    </>
  );
};

export default RequireAuth;
