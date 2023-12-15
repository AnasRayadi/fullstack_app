import { Outlet } from "react-router-dom";
import MainNavigation from "../components/navigation/MainNavigation";

const AppLayout = () => {
  return <>
    <MainNavigation />
    <Outlet />
    </>
}
export default AppLayout;
