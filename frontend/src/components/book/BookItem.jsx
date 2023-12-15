import React from 'react';

const BookItem = ({ book }) => {
  return (
    <div className='flex flex-col items-center'>
    <div className="flex flex-col items-center bg-white w-[50%] rounded-lg shadow-md p-4">
      <img src={book.image} alt={book.title} className="w-32 h-48 mx-auto mb-4" />
      <h2 className="text-xl font-bold mb-2">{book.title}</h2>
      <p className="text-gray-600">{book.author}</p>
      <p className="text-gray-600">{book.edition}</p>
      <p className="text-gray-600">{book.description}</p>
    </div>
    </div>
  );
};

export default BookItem;
