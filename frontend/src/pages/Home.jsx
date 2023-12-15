import React from 'react';

const Home = () => {
  return (
    <div className="bg-gray-900">
      <div className="container mx-auto px-4 py-16">
        <div className="text-center">
          <h1 className="text-4xl font-bold text-white">Welcome to My Full Stack App</h1>
          <p className="mt-4 text-lg text-gray-300">Build amazing applications with ease</p>
          <button className="mt-8 bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded">
            Get Started
          </button>
        </div>
      </div>
    </div>
  );
};

export default Home;
