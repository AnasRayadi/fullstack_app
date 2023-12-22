import React, { useState } from 'react';
import TableRow from './TableRow';
import DeleteBookPopUp from './DeleteBookPopUp';

const BooksTable = ({ books, refreshData }) => {
    const [showDeletePopUp, setShowDeletePopUp] = useState(false);
    const [bookToDelete, setBookToDelete] = useState(null); 

    const deleteHandler = (book) => {
        setShowDeletePopUp(true);
        setBookToDelete(book);
        // console.log(book);
    };

    const cancelHandler = () => {
        setShowDeletePopUp(false);
    };

    return (
        <div className='flex justify-center rounded-lg' >
            <table className="divide-y divide-gray-200 w-[70%] overflow-x-scroll mt-3 mb-4">
                <thead className="bg-gray-50">
                    <tr>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Image</th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Title</th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Author</th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Eddition</th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Category</th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                    </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                    {books.map((book) => (
                        <TableRow key={book.id} book={book} onDelete={deleteHandler} />
                    ))}
                </tbody>
            </table>
            {showDeletePopUp && <DeleteBookPopUp book={bookToDelete} refreshData={refreshData}  onCancel={cancelHandler}/>}
        </div>
    );
};

export default BooksTable;
