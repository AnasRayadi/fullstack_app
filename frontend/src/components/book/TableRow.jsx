import React from "react";
import { Link } from "react-router-dom";
import { extractRoles, getAccessToken } from "../../util/auth";

const TableRow = ({ book }) => {
  const token = getAccessToken();
  const role = extractRoles(token);
  const handleDelete = () => {
    // Handle delete logic here
  };

  return (
    <tr key={book.id}>
      <td className="px-6 py-4 whitespace-nowrap">
        <img src={book.image} alt={book.title} width={"70px"} height={"50px"} />
      </td>
      <td className="px-6 py-4 whitespace-nowrap">{book.title}</td>
      <td className="px-6 py-4 whitespace-nowrap">{book.author}</td>
      <td className="px-6 py-4 whitespace-nowrap">{book.edition}</td>
      <td className="px-6 py-4 whitespace-nowrap">
        {role.includes("ROLE_ADMIN") && (
          <Link to={`${book.id}/edit`} className="text-blue-500 hover:text-blue-700 mr-2">
            Edit
          </Link>
        )}
        {role.includes("ROLE_ADMIN") && (
          <button
            className="text-red-500 hover:text-red-700 mr-2"
            onClick={handleDelete}
          >
            Delete
          </button>
        )}
        <Link to={`${book.id}`} className="text-green-500 hover:text-green-700">
          Details
        </Link>
      </td>
    </tr>
  );
};
export default TableRow;
