import React from 'react';
import TableRow from './TableRow';

const BooksTable = ({ books }) => {
    return (
        <div className='flex justify-center rounded-lg' >
            <table className="divide-y divide-gray-200 w-[70%] overflow-x-scroll mt-3 mb-4">
                <thead className="bg-gray-50">
                    <tr>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Image</th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Title</th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Author</th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Eddition</th>
                        <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                    </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                    {books.map((book) => (
                        <TableRow key={book.id} book={book} />
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default BooksTable;
