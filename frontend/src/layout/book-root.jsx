import { Outlet } from "react-router-dom";
import BookNavigation from "../components/navigation/BookNavigation";

const BookLayout = () => {
  return (
    <>
      <div className="flex justify-center"> 
        <BookNavigation />
      </div>
      <Outlet />
    </>
  );
};
export default BookLayout;