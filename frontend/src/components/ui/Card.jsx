
import React from 'react';

const Card = ({ title, content }) => {
    return (
        <div className="bg-white shadow-md rounded-lg p-4">
            <h2 className="text-xl font-bold mb-2">{title}</h2>
            <p className="text-gray-600">{content}</p>
        </div>
    );
};

export default Card;
