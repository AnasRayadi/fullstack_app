import React from 'react';
import { Link, NavLink } from 'react-router-dom';

const BookNavigation = () => {
    return (
        <nav className="flex justify-center items-center w-[30%] ">
            <NavLink to={'/books'} 
            // className="text-blue-500 hover:text-blue-700"
            className={({ isActive }) => isActive ? "text-blue-500 p-4 hover:text-blue-700" : "text-gray-500 p-4 hover:text-gray-700"}
            end
            >
                All Books
            </NavLink>
            <NavLink to={'new'} 
            // className="text-blue-500 hover:text-blue-700"
            className={({ isActive }) => isActive ? "text-blue-500 p-4 hover:text-blue-700" : "text-gray-500 p-4 hover:text-gray-700"}
            end
            >
                New Book
            </NavLink>
        </nav>
    );
};

export default BookNavigation;
