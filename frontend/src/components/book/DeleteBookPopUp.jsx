import React from 'react';
import api from '../../api/axiosInterceptors';

const DeleteBookPopUp = ({ book, onCancel, refreshData }) => {
    const deleteHandler = async () => {
        // console.log(typeof(parseInt(book.id)));
        try {
            const res = await api.delete(`/books/${parseInt(book.id)}`);
            if (res.status === 200) {
                // window.location.reload();
                onCancel();
                refreshData();
            }
        } catch (error) {
            console.log(error);
        }

    };
    return (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
            <div className="bg-white p-4 rounded shadow-md">
                <h2 className="text-xl font-bold mb-4">Delete Book</h2>
                <p className="mb-4">Are you sure you want to delete the book "
                {book.title}
                "?</p>
                <div className="flex justify-end">
                    <button
                        className="px-4 py-2 mr-2 bg-red-500 text-white rounded hover:bg-red-600"
                        onClick={deleteHandler}
                    >
                        Delete
                    </button>
                    <button
                        className="px-4 py-2 bg-gray-300 text-gray-700 rounded hover:bg-gray-400"
                        onClick={() => onCancel()}
                    >
                        Cancel
                    </button>
                </div>
            </div>
        </div>
    );
};

export default DeleteBookPopUp;
